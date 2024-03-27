package services.recommend

import config.MongoConfig.{MONGO_DATABASE, MONGO_URI}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.udf
import services.DistanceCalculator

object UserCompanyDistanceService {

  private val calculateDistanceUDF = udf((lat1: Double, lon1: Double, lat2: Double, lon2: Double) => DistanceCalculator.calculateDistance(lat1, lon1, lat2, lon2))

  def calculateAllUserAllCompanyDistances(): Unit = {

    val spark = SparkSession.builder
      .appName("UserCompanyDistanceService")
      .master("local[*]")
      .config("spark.mongodb.input.uri", MONGO_URI)
      .config("spark.mongodb.output.uri", MONGO_URI)
      .getOrCreate()

    import spark.implicits._

    val userPositions = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "user")
      .load()
      .select("_id", "latitude", "longitude")
      .toDF("userId", "userLat", "userLon")

    val companyPositions = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "company")
      .load()
      .select("_id", "latitude", "longitude")
      .toDF("companyId", "companyLat", "companyLon")

    val userCompanyDistances = userPositions.crossJoin(companyPositions)
      .withColumn("distance", calculateDistanceUDF($"latitude", $"longitude", $"latitude", $"longitude"))
      .select("userId", "companyId", "distance")

    userCompanyDistances.write
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userCompanyDistance")
      .mode("overwrite")
      .save()

    spark.stop()
  }

  def calculateUserAllCompanyDistances(userId: Int): Double = {

    val spark = SparkSession.builder
      .appName("UserCompanyDistanceService")
      .master("local[*]")
      .config("spark.mongodb.input.uri", MONGO_URI)
      .config("spark.mongodb.output.uri", MONGO_URI)
      .getOrCreate()

    import spark.implicits._

    val userPosition = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "user")
      .load()
      .select("_id", "companyId", "latitude", "longitude")
      .where($"_id" === userId)
      .toDF("userId", "userLat", "userLon")

    val companyPosition = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "company")
      .load()
      .select("_id", "latitude", "longitude")
      .toDF("companyId", "companyLat", "companyLon")

    val userCompanyDistance = userPosition.crossJoin(companyPosition)
      .withColumn("distance", calculateDistanceUDF($"latitude", $"longitude", $"latitude", $"longitude"))
      .select("userId", "companyId", "distance")

    userCompanyDistance.write
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userCompanyDistance")
      .mode("append")
      .save()

    spark.stop()

  }
}
