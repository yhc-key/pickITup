package services.recommend

import com.typesafe.config.ConfigFactory
import config.MongoConfig.{MONGO_DATABASE, MONGO_URI}
import models.Recommendation
import org.apache.spark.ml.feature.CountVectorizer
import org.apache.spark.ml.linalg.{SparseVector, Vectors}
import org.apache.spark.ml.recommendation.ALS
import org.apache.spark.sql.functions.lit
import org.apache.spark.sql.{DataFrame, SparkSession}
import utils.SparkUtil

object ContentBasedFilteringService {

  case class JobPosting(jobId: Int, company: String, qualificationRequirements: Seq[String], preferredRequirements: Seq[String])

  def recommend(userId: Int): List[Recommendation] = {

    //    Logger.getLogger("org").setLevel(Level.ERROR)
    println("MONGO_URI: " + MONGO_URI)
    // Spark 세션 초기화
    val spark = SparkSession.builder
      .appName("TechStackSimilarity")
      .master("local[*]")
      .config("spark.mongodb.input.uri", MONGO_URI)
      .config("spark.mongodb.output.uri", MONGO_URI)
      .getOrCreate()

    import spark.implicits._

    // 예시 데이터 (실제 데이터 로딩 로직 필요)
    val userProfiles = Seq(
      (1, Seq("Java", "Spring", "Docker", "Kubernetes", "AWS", "MySQL", "Git")),
      (2, Seq("Python", "R", "TensorFlow", "Keras", "Pandas", "NumPy", "Scikit-learn"))
    ).toDF("userId", "techStack")
      .filter($"userId" === userId)

    //    val jobPostings0 = Seq(
    //      (1, "Backend Developer", Seq("Java", "Spring Boot", "MongoDB", "Docker", "AWS", "Git", "Jenkins"), Seq("Kubernetes", "Ansible", "Terraform")),
    //      (2, "Data Scientist", Seq("Python", "R", "SQL", "TensorFlow", "PyTorch"), Seq("Apache Spark", "Hadoop", "Keras"))
    //    ).toDF("jobId", "position", "requiredTechStack", "preferredTechStack")

    val recruits = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "recruit")
      .load()
      .select("_id", "qualificationRequirements", "preferredRequirements")
      .map(
        row => (
          row.getInt(0),
          row.getSeq[String](1) ++ row.getSeq[String](1) ++ row.getSeq[String](2))
      ).toDF("jobId", "techStack")

    //    val str = measureExecutionTime(calculateSimilarity(spark, userProfiles, jobPostingsDF))

    // CountVectorizer를 사용하여 기술 스택 벡터화
    val cvModel = new CountVectorizer()
      .setInputCol("techStack")
      .setOutputCol("features")
      .fit(userProfiles.select("techStack").union(recruits.select("techStack")))

    val userFeatures = cvModel.transform(userProfiles)
      .withColumnRenamed("features", "features_user")
    val jobFeatures = cvModel.transform(recruits)
      .withColumnRenamed("features", "features_recruit")
      .cache()

    // 유사도 계산
    val similarityScores = userFeatures.crossJoin(jobFeatures).map { row =>
        val userVec = row.getAs[SparseVector]("features_user")
        val jobVec = row.getAs[SparseVector]("features_recruit")
        val similarity = SparkUtil.cosineSimilarity(userVec, jobVec)
        (row.getAs[Int]("userId"), row.getAs[Int]("jobId"), similarity)
      }.toDF("userId", "jobId", "score")
      .select($"jobId", $"score")
      .sort($"score".desc)

    similarityScores
      .limit(20)
      .show()

    val recommendationList : List[Recommendation] = similarityScores
      .limit(10)
      .as[Recommendation]
      .collect()
      .toList

    spark.stop()

    recommendationList
  }

  private def contentBasedFiltering(): Unit = {

    val spark = SparkSession.builder
      .appName("TechStackSimilarity")
      .master("local[*]")
      .config("spark.mongodb.input.uri", "mongodb://localhost:27017/")
      .config("spark.mongodb.output.uri", "mongodb://localhost:27017/")
      .getOrCreate()

    import spark.implicits._

    val clicksDF: DataFrame = spark.read
      .format("mongo")
      .option("database", "recommend")
      .option("collection", "clicks")
      .load()
      .select("userId", "jobId", "clickCount")

    val scrapDF: DataFrame = spark.read
      .format("mongo")
      .option("database", "recommend")
      .option("collection", "scrap")
      .load()
      .select("userId", "jobId")
      .withColumn("scrap", lit(1))

    val ratingDF = clicksDF.join(scrapDF, Seq("userId", "jobId"), "outer")
      .na.fill(0)
      .withColumn("rating", ($"clickCount" + $"scrap" * 10).cast("double"))

    ratingDF.show()

    val als = new ALS()
      .setRank(10)
      .setMaxIter(10)
      .setRegParam(0.01)
      .setUserCol("userId")
      .setItemCol("jobId")
      .setRatingCol("rating")
      .setColdStartStrategy("drop")

    val model = als.fit(ratingDF)

    val prediction: DataFrame = model.recommendForAllUsers(10)

    prediction.show()
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
