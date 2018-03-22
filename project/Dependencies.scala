import Common._
import sbt._
import sbt.Keys._

object Dependencies {
  // Versions
  lazy val scalaTestVersion = "3.0.4"
  lazy val typesafeConfigVersion = "1.3.2"
  lazy val scalaCheckVersion = "1.13.5"

  //Spark and cassandra dependencies are set in library.sbt with runtime script

  // Testing libraries
  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
  val typesafeConfig = "com.typesafe" % "config" % typesafeConfigVersion
  val scalaCheck = "org.scalacheck" %% "scalacheck" % scalaCheckVersion
  val testDependencies: Seq[ModuleID] =
    Seq(scalaTest, typesafeConfig, scalaCheck).map(_ % "test")

}
