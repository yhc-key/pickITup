package services.recommend

import org.apache.spark.ml.stat.Correlation
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.sql.SparkSession

object CollaborativeFiltering {

  def recommend(): String = {

    val spark = SparkSession.builder
      .appName("CollaborativeFiltering")
      .master("local[*]")
      .config("spark.mongodb.input.uri", "mongodb://localhost:27017/")
      .getOrCreate()

    import spark.implicits._

    val clicks = spark.read
      .format("mongo")
      .option("uri", "mongodb://localhost:27017/")
      .option("database", "job-recommend")
      .option("collection", "clicks")
      .load()
      .select("userId", "jobId", "count")

    spark.stop()

    Correlation.corr(clicks, "count", "pearson").show()

    "Collaborative Filtering"
  }

}
