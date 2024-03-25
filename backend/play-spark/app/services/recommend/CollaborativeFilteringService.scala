package services.recommend

import config.MongoConfig.{MONGO_DATABASE, MONGO_URI}
import models.Recommendation
import org.apache.spark.sql.SparkSession

object CollaborativeFilteringService {

  case class userSimilarity(userId: Int, similarity: Double)

  def recommend(userId: Int): List[Recommendation] = {

    val spark = SparkSession.builder
      .appName("CollaborativeFiltering")
      .master("local[*]")
      .config("spark.mongodb.input.uri", MONGO_URI)
      .config("spark.mongodb.output.uri", MONGO_URI)
      .getOrCreate()

    import spark.implicits._

    val similarities = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userSimilarities")
      .load()
      .select("userId1", "userId2", "similarity")

    val top20SimilarUsers = similarities.filter($"userId1" === userId)
      .select("userId2", "similarity")
      .withColumnRenamed("userId2", "userId")
      .union(similarities.filter($"userId2" === userId)
        .select("userId1", "similarity")
        .withColumnRenamed("userId1", "userId")
      )
      .sort($"similarity".desc)
      .limit(20)

    val top20SimilarUsersList: Seq[Int] = top20SimilarUsers
      .map(_.getAs[Int]("userId"))
      .collect()
      .toSeq

    val interactionScores = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "interactionScores")
      .load()
      .select("userId", "jobId", "score")
      .filter($"userId".isin(top20SimilarUsersList: _*)) // top20SimilarUsers.map(_.userId) => List[Int]

    val recommendationDS = interactionScores
      .join(top20SimilarUsers, "userId")
      .withColumn("weightedScore", $"score" * $"similarity")
      .groupBy("jobId")
      .sum("weightedScore")
      .withColumnRenamed("sum(weightedScore)", "score")
      .sort($"score".desc)

    recommendationDS.show()

    val recommendationList = recommendationDS
      .as[Recommendation]
      .collect()
      .toList

    spark.stop()

    recommendationList
  }

}
