package models

case class Recommendation(userId: Int, jobId: Int, company: String, similarityScore: Double)
