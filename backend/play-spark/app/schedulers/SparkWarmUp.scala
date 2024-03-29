package schedulers

import akka.actor.ActorSystem
import utils.SparkUtil

import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class SparkWarmUp @Inject()(actorSystem: ActorSystem)(implicit executionContext: ExecutionContext){
  actorSystem.scheduler.scheduleAtFixedRate(initialDelay = 3.seconds, interval = 30.minutes) {
    () => {
      println("scheduled task executed.")
      val spark = SparkUtil.getOrCreateSparkSession()
      val sc = spark.sparkContext
      sc.parallelize(1 to 1000).count()
    }
  }
}
