name := """play-spark"""
organization := "com.ssafy"
maintainer := "j10a406"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.16"


libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies ++= Seq(
  guice,
  "com.typesafe" % "config" % "1.4.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,

  "org.mongodb.scala" %% "mongo-scala-driver" % "4.0.5",
  // Spark ( + MongoDB )
  "org.apache.spark" %% "spark-core" % "3.0.2",
  "org.apache.spark" %% "spark-sql" % "3.0.2",
  "org.apache.spark" %% "spark-mllib" % "3.0.2",
  "org.mongodb.spark" %% "mongo-spark-connector" % "3.0.2",
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.ssafy.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.ssafy.binders._"
