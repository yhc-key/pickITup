package utils

import org.apache.spark.ml.linalg.SparseVector

object SparkUtil {

  // 코사인 유사도 계산을 위한 사용자 정의 함수
  def cosineSimilarity(vectorA: SparseVector, vectorB: SparseVector): Double = {

    require(vectorA.size == vectorB.size, "Vector dimensions must match")

    val indicesA = vectorA.indices
    val valuesA = vectorA.values
    val indicesB = vectorB.indices
    val valuesB = vectorB.values


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
