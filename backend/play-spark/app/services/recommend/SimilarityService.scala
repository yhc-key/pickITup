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
      .option("database", MONGO_DATABASE)
      .option("collection", "user")
      .load()
      .select("_id", "keywords")
      .toDF("userId", "techStack")

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
      .option("database", MONGO_DATABASE)
      .option("collection", "user")
      .load()
      .select("_id", "keywords")
      .toDF("userId", "techStack")

    val cvModel = new CountVectorizer()
      .setInputCol("techStack")
      .setOutputCol("features")
      .fit(userProfiles.select("techStack"))

    val userFeatures = cvModel.transform(userProfiles)

    val newUser = userFeatures.filter($"userId" === userId)

    val userSimilarities = userFeatures
      .filter($"userId" =!= userId)
      .select("userId", "features")
      .map(row => {
        val otherUserId = row.getInt(0)
        val similarity = SparkUtil.cosineSimilarity(newUser.first().getAs[SparseVector]("features"), row.getAs[SparseVector]("features"))
        if (userId < otherUserId)
          (userId, otherUserId, similarity)
        else
          (otherUserId, userId, similarity)
      }).toDF("userId1", "userId2", "similarity")

    userSimilarities.show()

    userSimilarities.write
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userSimilaritiy")
      .mode("append")
      .save()

    spark.stop()

    "calculating user similarity is working!"
  }
}
