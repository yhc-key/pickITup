package services.mongo

import config.MongoConfig.{MONGO_DATABASE, MONGO_URI}
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._

import javax.inject.Singleton

@Singleton
class MongoService {

  private val mongoClient: MongoClient = MongoClient(MONGO_URI)
  private val database: MongoDatabase = mongoClient.getDatabase(MONGO_DATABASE)
  private val similarities: MongoCollection[Document] = database.getCollection("userSimilarities")
  private val distances: MongoCollection[Document] = database.getCollection("userCompanyDistance")

  def deleteUserSimilarities(userId: Int): SingleObservable[result.DeleteResult] = {
    val deleteFilter = or(equal("userId1", userId), equal("userId2", userId))
    similarities.deleteMany(deleteFilter)
  }

  def deleteUserCompanyDistances(userId: Int): SingleObservable[result.DeleteResult] = {
    print("userId: " + userId)
    val deleteFilter = equal("userId", userId)
    print(deleteFilter)
    distances.deleteMany(deleteFilter)
  }
}
