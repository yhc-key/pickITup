package services.recommend

import config.MongoConfig.MONGO_DATABASE
import org.apache.spark.sql.functions.udf
import utils.{DistanceCalculator, SparkUtil}

object UserCompanyDistanceService {

  private val calculateDistanceUDF = udf((lat1: Double, lon1: Double, lat2: Double, lon2: Double) => DistanceCalculator.calculateDistance(lat1, lon1, lat2, lon2))

  def calculateAllUserAllCompanyDistances(): Unit = {

    val spark = SparkUtil.getOrCreateSparkSession()

    import spark.implicits._

    val userPositions = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "user")
      .load()
      .select("_id", "latitude", "longitude")
      .toDF("userId", "userLat", "userLon")

    userPositions.show()

    val companyPositions = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "company")
      .load()
      .select("_id", "latitude", "longitude")
      .toDF("companyId", "companyLat", "companyLon")

    companyPositions.show()

    val userCompanyDistances = userPositions.crossJoin(companyPositions)
      .withColumn("distance", calculateDistanceUDF($"userLat", $"userLon", $"companyLat", $"companyLon"))
      .select("userId", "companyId", "distance")

    userCompanyDistances
      .sort("companyId")
      .show()

    userCompanyDistances.write
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userCompanyDistance")
      .mode("overwrite")
      .save()
  }
}
