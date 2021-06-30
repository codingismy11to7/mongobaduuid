name := "mongobaduuid"
version := "0.1.0"
scalaVersion := "2.13.6"

scalacOptions ++= Seq(
  "-feature",
  "-Xfatal-warnings",
  "-Xlint:unused",
  "-deprecation",
)

scalafmtOnCompile := true

val mongoVersion     = "1.0.4"
val scalatestVersion = "3.0.8"

evictionErrorLevel := Level.Warn

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "reactivemongo" % mongoVersion
) ++ Seq(
  "org.scalatest" %% "scalatest" % "3.0.8"
).map(_ % Test)
