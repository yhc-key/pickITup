package services.recommend

import config.MongoConfig.MONGO_DATABASE
import models.Recommendation
import org.apache.spark.ml.feature.{CountVectorizer, MinMaxScaler, VectorAssembler}
import org.apache.spark.ml.linalg.{SparseVector, Vector}
import org.apache.spark.sql.functions.{broadcast, lit, udf, when}
import utils.SparkUtil

object CollaborativeFilteringService {

  private val extractFirstElement = udf((vector: Vector) => vector.toArray(0))

  def recommend(userId: Int): List[Recommendation] = {

    val spark = SparkUtil.getOrCreateSparkSession()

    import spark.implicits._

    val similarities = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userSimilarity")
      .load()
      .select("userId1", "userId2", "similarity")

    val top50SimilarUsers = similarities.filter($"userId1" === userId)
      .select("userId2", "similarity")
      .withColumnRenamed("userId2", "userId")
      .union(similarities.filter($"userId2" === userId)
        .select("userId1", "similarity")
        .withColumnRenamed("userId1", "userId")
      )
      .filter($"similarity" > 0.4)
      .sort($"similarity".desc)
      .limit(50)

    val top50SimilarUsersList: Seq[Int] = top50SimilarUsers
      .map(_.getAs[Int]("userId"))
      .collect()
      .toSeq

    val userClicks = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "click")
      .load()
      .filter($"userId".isin(top50SimilarUsersList: _*))
      .persist()

    val userClickMax = userClicks
      .groupBy("userId")
      .max("clickCount")
      .withColumnRenamed("max(clickCount)", "maxClickCount")

    val userScaledClicks = userClicks
      .join(userClickMax, Seq("userId"), "left_outer")
      .withColumn("click", $"clickCount" / $"maxClickCount")
      .select("userId", "recruitId", "click")

    val userScraps = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "scrap")
      .load()
      .filter($"userId".isin(top50SimilarUsersList: _*))
      .withColumn("scrap", lit(1))

    val userInteractions = userScaledClicks
      .join(userScraps, Seq("userId", "recruitId"), "outer")
      .select("userId", "recruitId", "click", "scrap")
      .na.fill(0, Seq("click"))
      .na.fill(0, Seq("scrap"))
      .withColumn("score", $"click" +  $"scrap")

    val recruitInteractionScores = userInteractions
      .groupBy("recruitId")
      .sum("score")
      .withColumnRenamed("sum(score)", "interactionScore")
      .select("recruitId", "interactionScore")
      .persist()

    val userProfiles = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "user")
      .load()
      .select("_id", "keywords")
      .filter($"_id" === userId)
      .map(
        row => (
          row.getInt(0),
          row.getSeq[String](1))
      ).toDF("userId", "techStack")
      .persist()

    val userCompanyDistances = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "userCompanyDistance")
      .load()
      .select("userId", "companyId", "distance")
      .filter($"userId" === userId)
      .withColumn("distance", when($"distance".isNull, lit(Double.MaxValue)).otherwise($"distance"))
      .persist()

    // distance 컬럼을 벡터 컬럼으로 변환 (MinMaxScaler는 벡터 컬럼을 사용)
    val distanceAssembler = new VectorAssembler()
      .setInputCols(Array("distance"))
      .setOutputCol("distanceVector")
    val distanceVectorizedDF = distanceAssembler.transform(userCompanyDistances)

    // 정규화를 위한 MinMaxScaler (0 ~ 1 사이 값으로 변환)
    val distanceScalerModel = new MinMaxScaler()
      .setInputCol("distanceVector")
      .setOutputCol("scaledDistance")
      .setMax(1)
      .setMin(0)
      .fit(distanceVectorizedDF)

    val scaledDistance = distanceScalerModel.transform(distanceVectorizedDF)
      .withColumn("scaledDistance", extractFirstElement($"scaledDistance"))
      .select("companyId", "scaledDistance")

    val companies = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "company")
      .load()
      .select("_id", "name", "salary")
      .withColumn("salary", when($"salary" < 0, lit(40000000)).otherwise($"salary"))
      .toDF("companyId", "companyName", "salary")

    // salary 컬럼을 벡터 컬럼으로 변환 (MinMaxScaler는 벡터 컬럼을 사용)
    val assembler = new VectorAssembler()
      .setInputCols(Array("salary"))
      .setOutputCol("salaryVector")
    val companySalaryVectorizedDF = assembler.transform(companies)

    // 정규화를 위한 MinMaxScaler (0 ~ 1 사이 값으로 변환)
    val scalerModel = new MinMaxScaler()
      .setInputCol("salaryVector")
      .setOutputCol("scaledSalary")
      .setMax(1)
      .setMin(0)
      .fit(companySalaryVectorizedDF)

    val companyWithScaledSalary = scalerModel.transform(companySalaryVectorizedDF)
      .withColumn("scaledSalary", extractFirstElement($"scaledSalary"))
      .select("companyId", "companyName", "scaledSalary")

    val recruitDF = spark.read
      .format("mongo")
      .option("database", MONGO_DATABASE)
      .option("collection", "recruit")
      .load()
      .select("_id", "qualificationRequirements", "preferredRequirements", "companyId", "title", "dueDate", "url")
      .withColumnRenamed("_id", "recruitId")
      .persist()

    val recruits = recruitDF
      .map(
        row => (
          row.getInt(0),
          row.getSeq[String](1) ++ row.getSeq[String](1) ++ row.getSeq[String](2),
          row.getInt(3)
        )
      ).toDF("recruitId", "techStack", "companyId")
      .persist()

    // CountVectorizer를 사용하여 기술 스택 벡터화
    val cvModel = new CountVectorizer()
      .setInputCol("techStack")
      .setOutputCol("features")
      .fit(userProfiles.select("techStack")
        .union(recruits.select("techStack"))
      )

    val userFeatures = cvModel.transform(userProfiles)
      .withColumnRenamed("features", "features_user")
    val recruitFeatures = cvModel.transform(recruits)
      .withColumnRenamed("features", "features_recruit")
      .persist()

    // 유사도 계산
    val similarityScores = userFeatures.crossJoin(recruitFeatures).map { row =>
        val userVec = row.getAs[SparseVector]("features_user")
        val jobVec = row.getAs[SparseVector]("features_recruit")
        val similarity = SparkUtil.cosineSimilarity(userVec, jobVec)
        (row.getAs[Int]("recruitId"), row.getAs[Int]("companyId"), similarity)
      }.toDF("recruitId", "companyId", "similarityScore")
      .filter($"similarityScore" > 0.3)
      .persist()


    val totalDF = similarityScores
      .join(companyWithScaledSalary, "companyId")
      .join(broadcast(scaledDistance), Seq("companyId"), "left_outer")
      .join(recruitInteractionScores, Seq("recruitId"), "left_outer")
      .na.fill(0, Seq("interactionScore"))
      .persist()

    val intersectionBetweenUserAndRecruit = udf((userTechStack: Seq[String],
                                                 qualificationRequirement: Seq[String],
                                                 preferredRequirements: Seq[String]) => {
      val unionSeq = (qualificationRequirement ++ preferredRequirements).distinct
      userTechStack.intersect(unionSeq)
    })

    val recommendationDF = totalDF
      .select("recruitId", "companyName", "similarityScore", "scaledSalary", "scaledDistance", "interactionScore")
      .withColumn("totalScore", $"similarityScore".*(8) + $"scaledSalary" - $"scaledDistance" + $"interactionScore")
      .sort($"totalScore".desc)
      .limit(20)
      .join(recruitDF, "recruitId")
      .crossJoin(broadcast(userProfiles))
      .withColumn("intersection", intersectionBetweenUserAndRecruit($"techStack", $"qualificationRequirements", $"preferredRequirements"))
      .withColumnRenamed("companyName", "company")
      .join(broadcast(userCompanyDistances), "companyId")
      .select("recruitId", "url", "company", "intersection", "distance", "totalScore", "dueDate", "title", "qualificationRequirements", "preferredRequirements")
      .sort($"totalScore".desc)

    val recommendationList: List[Recommendation] = recommendationDF
      .as[Recommendation]
      .collect()
      .toList

    recruitInteractionScores.unpersist()
    recruitDF.unpersist()
    recruits.unpersist()
    userProfiles.unpersist()
    userCompanyDistances.unpersist()
    totalDF.unpersist()

    recommendationList
  }

}
