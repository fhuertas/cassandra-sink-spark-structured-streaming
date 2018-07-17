import sbt._

organization in ThisBuild := "com.fhuertas"
scalaVersion in ThisBuild := "2.11.12"
Test / fork := true

lazy val `cassandra-sink_220` = project
  .in(file("versions/2.2.0"))
  .settings(moduleName := "cassandra-sink_2.2.0")
  .settings(version220Settings)

lazy val `cassandra-sink_221` = project
  .in(file("versions/2.2.1"))
  .settings(moduleName := "cassandra-sink_2.2.1")
  .settings(version221Settings)
  .dependsOn(`cassandra-sink_220`)

lazy val allModules: Seq[ProjectReference] = Seq(`cassandra-sink_220`, `cassandra-sink_221`)

lazy val `cassandra-sink` = project
  .in(file("."))
  .aggregate(allModules: _*)


lazy val root = project
  .in(file("."))
  .settings(
    name := "cassandra-sink",
    scalaVersion := "2.11.12",
    coverageMinimum := 95,
    coverageFailOnMinimum := true
  )
  .aggregate(allModules: _*)

addCommandAlias("ci-all",  ";+clean ;+compile ;+test ;+package")
addCommandAlias("release", ";+publishSigned ;sonatypeReleaseAll")
