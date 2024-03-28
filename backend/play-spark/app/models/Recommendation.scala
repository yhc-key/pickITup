package models

case class Recommendation(recruitId: Int,
                          company: String,
                          intersection: Seq[String],
                          distance: Double)
