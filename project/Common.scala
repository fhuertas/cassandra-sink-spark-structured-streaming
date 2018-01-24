import sbt.Keys._
import sbt._

object Common {
  lazy val scalaMayorVersion = "2.11"
  lazy val scalaMinorVersion = s"$scalaMayorVersion.12"
  lazy val sparkVersion = "2.2.0"


  lazy val nameBase = "cassandra-sink_"
  lazy val `name_2.2.0` = s"${nameBase}2.2.0"
  lazy val `name_2.2.1` = s"${nameBase}2.2.1"

  lazy val settings: Seq[Def.Setting[_]] = Seq(
    version := "1.0.0-SNAPSHOT",
    scalaVersion := scalaMinorVersion,
    organization := "com.fhuertas.spark.sql",
    libraryDependencies ++= Dependencies.dependencies ++
      Dependencies.testDependencies ++
      Dependencies.providedDependencies
  )
}