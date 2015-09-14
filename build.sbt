name := """simple-http4s-api"""

version := "1.0"

scalaVersion := "2.11.7"

val http4sVersion = "0.10.0"
val circeVersion = "0.1.1"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-argonaut" % http4sVersion,
  "com.typesafe.play" %% "play-json" % "2.3.4",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-jawn" % circeVersion,
  "com.chuusai" %% "shapeless" % "2.2.1",
  "org.scalatest" %% "scalatest" % "2.2.4"
)
