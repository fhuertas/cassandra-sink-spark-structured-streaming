import sbt._

organization in ThisBuild := "com.fhuertas"
scalaVersion in ThisBuild := "2.11.12"
Test / fork in ThisBuild := true

lazy val common = project
  .in(file("common"))
  .settings(moduleName := "cassandra-sink_common")
  .settings(commonSettings)

lazy val `cassandra-sink_220` = project
  .in(file("versions/2.2.0"))
  .settings(moduleName := "cassandra-sink_2.2.0")
  .settings(version220Settings)
  .dependsOn(common % "test->test")

lazy val `cassandra-sink_221` = project
  .in(file("versions/2.2.1"))
  .settings(moduleName := "cassandra-sink_2.2.1")
  .settings(version221Settings)
  .dependsOn(`cassandra-sink_220`,common % "test->test")

lazy val `cassandra-sink_230` = project
  .in(file("versions/2.3.0"))
  .settings(moduleName := "cassandra-sink_2.3.0")
  .settings(version230Settings)
  .dependsOn(common % "test->test")

lazy val `cassandra-sink_231` = project
  .in(file("versions/2.3.1"))
  .settings(moduleName := "cassandra-sink_2.3.1")
  .settings(version231Settings)
  .dependsOn(`cassandra-sink_230`,common % "test->test")

lazy val `cassandra-sink_232` = project
  .in(file("versions/2.3.2"))
  .settings(moduleName := "cassandra-sink_2.3.2")
  .settings(version232Settings)
  .dependsOn(`cassandra-sink_230`,common % "test->test")

lazy val allModules: Seq[ProjectReference] = Seq(common,
  `cassandra-sink_220`,
  `cassandra-sink_221`,
  `cassandra-sink_230`,
  `cassandra-sink_231`,
  `cassandra-sink_232`)

lazy val `cassandra-sink` = project
  .in(file("."))
  .aggregate(allModules: _*)


lazy val root = project
  .in(file("."))
  .settings(
    name := "cassandra-sink",
    scalaVersion := V.Scala211,
    coverageMinimum := 90,
    coverageFailOnMinimum := true,
    publishArtifact := false
  )
  .aggregate(allModules: _*)

addCommandAlias("ci-all", ";+clean; +coverage; +test; +coverageReport; coverageAggregate")
addCommandAlias("release", ";+publishSigned; sonatypeReleaseAll")
