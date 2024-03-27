package services.mongo

import config.MongoConfig.{MONGO_DATABASE, MONGO_URI}
import org.mongodb.scala._
import org.mongodb.scala.model.Filters._

import javax.inject.Singleton

@Singleton
class MongoService {

  private val mongoClient: MongoClient = MongoClient(MONGO_URI)
  private val database: MongoDatabase = mongoClient.getDatabase(MONGO_DATABASE)
  private val collection: MongoCollection[Document] = database.getCollection("userSimilarities")

  def deleteUserSimilarities(userId: Int): SingleObservable[result.DeleteResult] = {
    val deleteFilter = or(equal("userId1", userId), equal("userId2", userId))
    collection.deleteMany(deleteFilter)
  }
}
