package config

import com.typesafe.config.ConfigFactory

object MongoConfig {

  private val config = ConfigFactory.load()
  private val mongoConfig = config.getConfig("mongo")

  //  private val mongoHostname: String = "localhost"
  private val mongoHostname: String = mongoConfig.getString("hostname")
  private val mongoPort: String = mongoConfig.getString("port")
  private val mongoUsername: String = mongoConfig.getString("username")
  private val mongoPassword: String = mongoConfig.getString("password")

  val MONGO_URI: String = s"mongodb://${mongoUsername}:${mongoPassword}@${mongoHostname}:${mongoPort}/"
  val MONGO_DATABASE: String = mongoConfig.getString("database")
}
