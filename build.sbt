name := """simple-http4s-api"""

version := "1.0"

scalaVersion := "2.11.7"

val http4sVersion = "0.10.0"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  "org.http4s" %% "http4s-argonaut"     % http4sVersion
)
