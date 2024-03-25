package services.recommend

import com.typesafe.config.ConfigFactory
import config.MongoConfig.{MONGO_DATABASE, MONGO_URI}
import org.apache.spark.ml.feature.CountVectorizer
import org.apache.spark.ml.linalg.SparseVector
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.udf
import utils.SparkUtil

object SimilarityService {

  def calculateAllUserSimilarities(): String = {

    val spark = SparkSession.builder
      .appName("AllUserSimilarities")
      .master("local[*]")
      .config("spark.mongodb.input.uri", MONGO_URI)
      .config("spark.mongodb.output.uri", MONGO_URI)
      .getOrCreate()

    import spark.implicits._

    val userProfiles = spark.read
      .format("mongo")
      .option("collection", "userProfiles")
      .load()
      .select("userId", "techStack")

    val cvModel = new CountVectorizer()
      .setInputCol("techStack")
      .setOutputCol("features")
      .fit(userProfiles.select("techStack"))

    val userFeatures = cvModel.transform(userProfiles)

    userFeatures.show()

    val userPairs = userFeatures.as("user1")
      .crossJoin(userFeatures.as("user2"))
      .filter($"user1.userId" < $"user2.userId")

    userPairs.show()

    val calculateSimilarityUDF = udf((v1: SparseVector, v2: SparseVector) => SparkUtil.cosineSimilarity(v1, v2))

    val similarityDF = userPairs
      .withColumn("similarity", calculateSimilarityUDF($"user1.features", $"user2.features"))
      .select(
        $"user1.userId".as("userId1"),
        $"user2.userId".as("userId2"),
        $"similarity"
      )

    similarityDF.show()

    similarityDF.write
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userSimilarity")
      .mode("overwrite")
      .save()

    spark.stop()
    "calculating all user similarities is working!"
  }

  def calculateUserSimilarity(userId: Int): String = {

    val spark = SparkSession.builder
      .appName("UserSimilarity")
      .master("local[*]")
      .config("spark.mongodb.input.uri", MONGO_URI)
      .config("spark.mongodb.output.uri", MONGO_URI)
      .getOrCreate()

    import spark.implicits._

    val userProfiles = spark.read
      .format("mongo")
      .option("collection", "userProfiles")
      .load()
      .select("userId", "techStack")

    val cvModel = new CountVectorizer()
      .setInputCol("techStack")
      .setOutputCol("features")
      .fit(userProfiles.select("techStack"))

    val userFeatures = cvModel.transform(userProfiles)

    val newUser = userFeatures.filter($"userId" === userId)

    val userSimilarities = userFeatures
      .filter($"userId" =!= userId)
      .select("userId", "features")
      .map(row => (
        row.getInt(0),
        SparkUtil.cosineSimilarity(newUser.first().getAs[SparseVector]("features"), row.getAs[SparseVector]("features"))
      )).toDF("userId", "similarity")

    userSimilarities.show()

    userSimilarities.write
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userSimilarities")
      .mode("append")
      .save()

    spark.stop()

    "calculating user similarity is working!"
  }

  def calculateRecruitSimilarity(): String = {

    val spark = SparkSession.builder
      .appName("AllRecruitSimilarities")
      .master("local[*]")
      .config("spark.mongodb.input.uri", MONGO_URI)
      .config("spark.mongodb.output.uri", MONGO_URI)
      .getOrCreate()

    import spark.implicits._

    val recruitDF = spark.read
      .format("mongo")
      .option("collection", "recruit")
      .load()
      .select("_id", "company", "qualificationRequirements", "preferredRequirements")

    val recruits = recruitDF.map(
      row => (
        row.getInt(0),
        row.getString(1),
        row.getSeq[String](2) ++ row.getSeq[String](2) ++ row.getSeq[String](3))
    ).toDF("jobId", "company", "techStack")

    recruits.select("techStack").show(30)

    val cvModel = new CountVectorizer()
      .setInputCol("techStack")
      .setOutputCol("features")
      .fit(recruits.select("techStack"))

    val jobFeatures = cvModel.transform(recruits)

//    jobFeatures.show()

    val jobPairs = jobFeatures.as("job1")
      .crossJoin(jobFeatures.as("job2"))
      .filter($"job1.jobId" < $"job2.jobId")

//    jobPairs.show()

    val calculateSimilarityUDF = udf((v1: SparseVector, v2: SparseVector) => SparkUtil.cosineSimilarity(v1, v2))

    val similarityDF = jobPairs
      .withColumn("similarity", calculateSimilarityUDF($"job1.features", $"job2.features"))
      .select(
        $"job1.jobId".as("jobId1"),
        $"job1.company".as("company1"),
        $"job2.jobId".as("jobId2"),
        $"job2.company".as("company2"),
        $"similarity"
      ).sort($"similarity".desc, $"jobId1")

    similarityDF.show(30)

    similarityDF.write
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "recruitSimilarity")
      .mode("overwrite")
      .save()

    spark.stop()
    "calculate recruit similarity is working!"
  }
}
