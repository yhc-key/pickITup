package services.recommend

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.feature.CountVectorizer
import org.apache.spark.ml.linalg.SparseVector
import org.apache.spark.sql.{DataFrame, SparkSession}
import play.api.Configuration

import javax.inject.{Inject, Singleton}

@Singleton
class ContentBasedFilteringServiceClass @Inject()(config: Configuration) {

//  private val mongoHostname: String = config.get[String]("mongo.hostname")
  private val mongoHostname: String = "localhost"
  private val mongoPort: String = config.get[String]("mongo.port")
  private val mongoDatabase: String = config.get[String]("mongo.database")
  private val mongoUsername: String = config.get[String]("mongo.username")
  private val mongoPassword: String = config.get[String]("mongo.password")

  def recommend(): String = {

//    Logger.getLogger("org").setLevel(Level.ERROR)

    val mongoUri: String = s"mongodb://${mongoUsername}:${mongoPassword}@${mongoHostname}:${mongoPort}/"

    // Spark 세션 초기화
    @transient lazy val spark = SparkSession.builder
      .appName("TechStackSimilarity")
      .master("local[*]")
      .config("spark.mongodb.input.uri", mongoUri)
      .config("spark.mongodb.output.uri", mongoUri)
      .getOrCreate()

    import spark.implicits._

    // 예시 데이터 (실제 데이터 로딩 로직 필요)
    val userProfiles = Seq(
      (1, "Backend Developer", Seq("Java", "Spring", "Docker", "Kubernetes", "AWS", "MySQL", "Git")),
      (2, "Data Scientist", Seq("Python", "R", "TensorFlow", "Keras", "Pandas", "NumPy", "Scikit-learn"))
    ).toDF("userId", "position", "techStack")

    //    val jobPostings0 = Seq(
    //      (1, "Backend Developer", Seq("Java", "Spring Boot", "MongoDB", "Docker", "AWS", "Git", "Jenkins"), Seq("Kubernetes", "Ansible", "Terraform")),
    //      (2, "Data Scientist", Seq("Python", "R", "SQL", "TensorFlow", "PyTorch"), Seq("Apache Spark", "Hadoop", "Keras"))
    //    ).toDF("jobId", "position", "requiredTechStack", "preferredTechStack")

    val jobPostingsDF = spark.read
      .format("mongo")
      .option("database", mongoDatabase)
      .option("collection", "jobPostings")
      .load()
      .select("_id", "company", "qualificationRequirements", "preferredRequirements")

//    val str = measureExecutionTime(calculateSimilarity(spark, userProfiles, jobPostingsDF))
val jobPostings = jobPostingsDF.map(
  row => (
    row.getInt(0),
    row.getString(1),
    row.getSeq[String](2) ++ row.getSeq[String](2) ++ row.getSeq[String](3))
).toDF("jobId", "company", "techStack")

    // CountVectorizer를 사용하여 기술 스택 벡터화
    val cvModel = new CountVectorizer()
      .setInputCol("techStack")
      .setOutputCol("features")
      .fit(userProfiles.select("techStack").union(jobPostings.select("techStack")))

    val userFeatures = cvModel.transform(userProfiles)
      .toDF("userId", "position", "techStack", "features_user")
    val jobFeatures = cvModel.transform(jobPostings)
      .toDF("jobId", "company", "techStack", "features_job")
      .cache()

    // 유사도 계산
    val similarityScores = userFeatures.crossJoin(jobFeatures).map { row =>
        val userVec = row.getAs[SparseVector]("features_user")
        val jobVec = row.getAs[SparseVector]("features_job")
        val similarity = cosineSimilarity(userVec, jobVec)
        (row.getAs[Int]("userId"), row.getAs[Int]("jobId"), row.getAs[String]("company"), similarity) // (userId, jobId, company, similarityScore)
      }.toDF("userId", "jobId", "company", "similarityScore")
      .sort($"userId", $"similarityScore".desc)

    similarityScores
      .filter($"userId" === 1)
      .limit(10)
      .show()

    similarityScores
      .filter($"userId" === 2)
      .limit(10)
      .show()

    spark.stop()
    similarityScores.collect().mkString
  }

  // 코사인 유사도 계산을 위한 사용자 정의 함수
  private def cosineSimilarity(vectorA: SparseVector, vectorB: SparseVector): Double = {

    require(vectorA.size == vectorB.size, "Vector dimensions must match")

    val indicesA = vectorA.indices
    val valuesA = vectorA.values
    val indicesB = vectorB.indices
    val valuesB = vectorB.values


    var dotProduct = 0.0
    var normA = 0.0
    var normB = 0.0

    // dot product
    var i = 0
    var j = 0
    while (i < indicesA.length && j < indicesB.length) {
      if (indicesA(i) == indicesB(j)) {
        dotProduct += valuesA(i) * valuesB(j)
        i += 1
        j += 1
      } else if (indicesA(i) < indicesB(j)) {
        i += 1
      } else {
        j += 1
      }
    }

    //      println(s"dotProduct = $dotProduct")
    // norm
    normA = math.sqrt(valuesA.map(math.pow(_, 2)).sum)
    normB = math.sqrt(valuesB.map(math.pow(_, 2)).sum)

    if (normA * normB == 0) 0.0 else dotProduct / (normA * normB)
  }

  private def calculateSimilarity(spark: SparkSession, userProfiles: DataFrame, jobPostingsDF: DataFrame): String = {

    import spark.implicits._

    val jobPostings = jobPostingsDF.map(
      row => (
        row.getInt(0),
        row.getString(1),
        row.getSeq[String](2) ++ row.getSeq[String](2) ++ row.getSeq[String](3))
    ).toDF("jobId", "company", "techStack")

    // CountVectorizer를 사용하여 기술 스택 벡터화
    val cvModel = new CountVectorizer()
      .setInputCol("techStack")
      .setOutputCol("features")
      .fit(userProfiles.select("techStack").union(jobPostings.select("techStack")))

    val userFeatures = cvModel.transform(userProfiles)
      .toDF("userId", "position", "techStack", "features_user")
    val jobFeatures = cvModel.transform(jobPostings)
      .toDF("jobId", "company", "techStack", "features_job")
      .cache()

    // 유사도 계산
    val similarityScores = userFeatures.crossJoin(jobFeatures).map { row =>
        val userVec = row.getAs[SparseVector]("features_user")
        val jobVec = row.getAs[SparseVector]("features_job")
        val similarity = cosineSimilarity(userVec, jobVec)
        (row.getAs[Int]("userId"), row.getAs[Int]("jobId"), row.getAs[String]("company"), similarity) // (userId, jobId, company, similarityScore)
      }.toDF("userId", "jobId", "company", "similarityScore")
      .sort($"userId", $"similarityScore".desc)

    similarityScores
      .filter($"userId" === 1)
      .limit(10)
      .show()

    similarityScores
      .filter($"userId" === 2)
      .limit(10)
      .show()

    similarityScores.collect().mkString
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
