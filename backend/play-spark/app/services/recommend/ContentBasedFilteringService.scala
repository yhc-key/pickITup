package services.recommend

import com.typesafe.config.ConfigFactory
import config.MongoConfig.{MONGO_DATABASE, MONGO_URI}
import models.Recommendation
import org.apache.spark.ml.feature.{CountVectorizer, MinMaxScaler, VectorAssembler}
import org.apache.spark.ml.linalg.{SparseVector, Vector}
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.functions.{lit, udf, when}
import org.apache.spark.sql.{DataFrame, SparkSession}
import utils.SparkUtil

object ContentBasedFilteringService {

  case class JobPosting(jobId: Int, company: String, qualificationRequirements: Seq[String], preferredRequirements: Seq[String])

  def recommend(userId: Int): List[Recommendation] = {

    //    Logger.getLogger("org").setLevel(Level.ERROR)
    // Spark 세션 초기화
    val spark = SparkSession.builder
      .appName("TechStackSimilarity")
      .master("local[*]")
      .config("spark.mongodb.input.uri", MONGO_URI)
      .config("spark.mongodb.output.uri", MONGO_URI)
      .getOrCreate()

    import spark.implicits._

    val extractFirstElement = udf((vector: Vector) => vector.toArray(0))
    val inverseValue = udf((value: Double) => 1.0 - value)

    // 예시 데이터 (실제 데이터 로딩 로직 필요)
    //    val userProfiles = Seq(
    //      (1, Seq("Java", "Spring", "Docker", "Kubernetes", "AWS", "MySQL", "Git")),
    //      (2, Seq("Python", "R", "TensorFlow", "Keras", "Pandas", "NumPy", "Scikit-learn"))
    //    ).toDF("userId", "techStack")
    //      .filter($"userId" === userId)

    val userProfiles = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "user")
      .load()
      .select("_id", "keywords")
      .filter($"_id" === userId)
      .map(
        row => (
          row.getInt(0),
          row.getSeq[String](1))
      ).toDF("userId", "techStack")

    val userCompanyDistances = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userCompanyDistance")
      .load()
      .select("userId", "companyId", "distance")
      .filter($"userId" === userId)
      .filter($"distance".isNotNull)

    // distance 컬럼을 벡터 컬럼으로 변환 (MinMaxScaler는 벡터 컬럼을 사용)
    val distanceAssembler = new VectorAssembler()
      .setInputCols(Array("distance"))
      .setOutputCol("distanceVector")
    val distanceVectorizedDF = distanceAssembler.transform(userCompanyDistances)

    // 정규화를 위한 MinMaxScaler (0 ~ 1 사이 값으로 변환)
    val distanceScalerModel = new MinMaxScaler()
      .setInputCol("distanceVector")
      .setOutputCol("scaledDistance")
      .setMax(1)
      .setMin(0)
      .fit(distanceVectorizedDF)

    val scaledDistanceFactor = distanceScalerModel.transform(distanceVectorizedDF)
      .withColumn("scaledDistance", extractFirstElement($"scaledDistance"))
      .withColumn("scaledDistance", inverseValue($"scaledDistance"))
      .select("companyId", "scaledDistance")
      .withColumnRenamed("socialDistance", "socialDistanceFactor")

    //    scaledDistanceFactor.show()

    //    val jobPostings0 = Seq(
    //      (1, "Backend Developer", Seq("Java", "Spring Boot", "MongoDB", "Docker", "AWS", "Git", "Jenkins"), Seq("Kubernetes", "Ansible", "Terraform")),
    //      (2, "Data Scientist", Seq("Python", "R", "SQL", "TensorFlow", "PyTorch"), Seq("Apache Spark", "Hadoop", "Keras"))
    //    ).toDF("jobId", "position", "requiredTechStack", "preferredTechStack")

    val companies = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "company")
      .load()
      .select("_id", "name", "salary")
      .withColumn("salary", when($"salary" < 0, lit(40000000)).otherwise($"salary"))
      .toDF("companyId", "companyName", "salary")

    // salary 컬럼을 벡터 컬럼으로 변환 (MinMaxScaler는 벡터 컬럼을 사용)
    val assembler = new VectorAssembler()
      .setInputCols(Array("salary"))
      .setOutputCol("salaryVector")
    val companySalaryVectorizedDF = assembler.transform(companies)

    // 정규화를 위한 MinMaxScaler (0 ~ 1 사이 값으로 변환)
    val scalerModel = new MinMaxScaler()
      .setInputCol("salaryVector")
      .setOutputCol("scaledSalary")
      .setMax(1)
      .setMin(0)
      .fit(companySalaryVectorizedDF)

    val companyWithScaledSalary = scalerModel.transform(companySalaryVectorizedDF)
      .withColumn("scaledSalary", extractFirstElement($"scaledSalary"))
      .select("companyId", "companyName", "scaledSalary")

    //    companyWithScaledSalary
    //      .sort($"scaledSalary".desc)
    //      .show()

    val recruitDF = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "recruit")
      .load()
      .select("_id", "qualificationRequirements", "preferredRequirements", "companyId")
      .withColumnRenamed("_id", "recruitId")

    val recruits = recruitDF
      .map(
        row => (
          row.getInt(0),
          row.getSeq[String](1) ++ row.getSeq[String](1) ++ row.getSeq[String](2),
          row.getInt(3)
        )
      ).toDF("recruitId", "techStack", "companyId")


    //    val str = measureExecutionTime(calculateSimilarity(spark, userProfiles, jobPostingsDF))
    //    userProfiles.show()
    //    recruits.show()
    // CountVectorizer를 사용하여 기술 스택 벡터화
    val cvModel = new CountVectorizer()
      .setInputCol("techStack")
      .setOutputCol("features")
      .fit(userProfiles.select("techStack")
        .union(recruits.select("techStack"))
      )

    val userFeatures = cvModel.transform(userProfiles)
      .withColumnRenamed("features", "features_user")
    val recruitFeatures = cvModel.transform(recruits)
      .withColumnRenamed("features", "features_recruit")
      .cache()

    // 유사도 계산
    val similarityScores = userFeatures.crossJoin(recruitFeatures).map { row =>
      val userVec = row.getAs[SparseVector]("features_user")
      val jobVec = row.getAs[SparseVector]("features_recruit")
      val similarity = SparkUtil.cosineSimilarity(userVec, jobVec)
      (row.getAs[Int]("recruitId"), row.getAs[Int]("companyId"), similarity)
    }.toDF("recruitId", "companyId", "similarityScore")


    val totalDF = similarityScores
      .join(companyWithScaledSalary, "companyId")
      .join(scaledDistanceFactor, Seq("companyId"), "left_outer")

    val calculateTotalScore = udf((similarityScore: Double, scaledSalary: Double, scaledDistance: Double) => {
      80 * similarityScore + 10 * scaledSalary + 10 * scaledDistance
    })

    val intersectionBetweenUserAndRecruit = udf((userTechStack: Seq[String],
                                                 qualificationRequirement: Seq[String],
                                                 preferredRequirements: Seq[String]) => {
      val unionSeq = (qualificationRequirement ++ preferredRequirements).distinct
      userTechStack.intersect(unionSeq)
    })

    val recommendationDF = totalDF
      .select("recruitId", "companyName", "similarityScore", "scaledSalary", "scaledDistance")
      .withColumn("scaledDistance", when($"scaledDistance".isNull, lit(0.0)).otherwise($"scaledDistance"))
      .withColumnRenamed("companyName", "company")
      .withColumnRenamed("scaledSalary", "salaryScore")
      .withColumnRenamed("scaledDistance", "distanceScore")
      .withColumn("totalScore", calculateTotalScore($"similarityScore", $"salaryScore", $"distanceScore"))
      .sort($"totalScore".desc)
      .limit(20)
      .join(recruitDF, "recruitId")
      .crossJoin(userProfiles)
      .withColumn("intersection", intersectionBetweenUserAndRecruit($"techStack", $"qualificationRequirements", $"preferredRequirements"))
      .join(userCompanyDistances, "companyId")
      .select("recruitId", "company", "intersection", "distance", "totalScore")
      .sort($"totalScore".desc)
      .cache()


    //    recommendationDF
    //      .limit(20)
    //      .show()

    val recommendationList: List[Recommendation] = recommendationDF
      .limit(20)
      .as[Recommendation]
      .collect()
      .toList

    spark.stop()

    recommendationList
  }

  private def measureExecutionTime[T](block: => T): T = {
    val startTime = System.nanoTime()
    val result = block // 측정하고 싶은 코드 블록 실행
    val endTime = System.nanoTime()
    val duration = endTime - startTime
    println(s"Execution time: ${duration / 1e6} ms")
    result // 코드 블록의 실행 결과를 반환
  }
}
