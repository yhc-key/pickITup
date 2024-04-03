package models

import org.apache.spark.sql.types.TimestampType

import java.sql.Timestamp

case class Recommendation(recruitId: Int,
                          company: String,
                          intersection: Seq[String],
                          distance: Double,
                          qualificationRequirements: Seq[String],
                          preferredRequirements: Seq[String],
                          dueDate: Timestamp,
                          title: String,
                          url: String
                         )
