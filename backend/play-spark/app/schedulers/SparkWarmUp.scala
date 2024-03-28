package schedulers

import akka.actor.ActorSystem
import config.MongoConfig.MONGO_URI
import org.apache.spark.sql.SparkSession

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class SparkWarmUp @Inject()(actorSystem: ActorSystem)(implicit executionContext: ExecutionContext){
  actorSystem.scheduler.scheduleAtFixedRate(initialDelay = 3.seconds, interval = 30.minutes) {
    () => {
      println("scheduled task executed.")
      val spark = SparkSession.builder
        .appName("TechStackSimilarity")
        .master("local[*]")
        .config("spark.mongodb.input.uri", MONGO_URI)
        .config("spark.mongodb.output.uri", MONGO_URI)
        .getOrCreate()
      val sc = spark.sparkContext
      sc.parallelize(1 to 1000).count()
    }
  }
}
