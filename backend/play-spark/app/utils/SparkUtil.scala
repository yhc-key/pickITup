package utils

import config.MongoConfig.MONGO_URI
import org.apache.spark.ml.linalg.SparseVector
import org.apache.spark.sql.SparkSession

object SparkUtil {

  // 이미 생성된 Spark 세션을 반환하거나 새로운 세션을 생성
  def getOrCreateSparkSession(): SparkSession = {
    val spark = SparkSession.builder
      .appName("PlaySpark")
      .master("local[*]")
      .config("spark.mongodb.input.uri", MONGO_URI)
      .config("spark.mongodb.output.uri", MONGO_URI)
      .getOrCreate()
    spark.newSession()
  }

  // 코사인 유사도 계산을 위한 사용자 정의 함수
  def cosineSimilarity(vectorA: SparseVector, vectorB: SparseVector): Double = {

    require(vectorA.size == vectorB.size, "Vector dimensions must match")

    val indicesA = vectorA.indices
    val valuesA = vectorA.values
    val indicesB = vectorB.indices
    val valuesB = vectorB.values

    if (indicesA.isEmpty || indicesB.isEmpty) {
      return 0.0
    }

    if (indicesA.sameElements(indicesB) && valuesA.sameElements(valuesB)) {
      return 1.0
    }

    var dotProduct = 0.0

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
    val normA = math.sqrt(valuesA.map(math.pow(_, 2)).sum)
    val normB = math.sqrt(valuesB.map(math.pow(_, 2)).sum)

    if (normA * normB == 0) 0.0 else dotProduct / (normA * normB)
  }
}
