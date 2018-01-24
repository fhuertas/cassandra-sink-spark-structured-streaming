import Common._
import sbt._

object Dependencies {
  // Versions
  lazy val holdenkarauSparkTestingVersion = s"${sparkVersion}_0.8.0"
  lazy val scalaTestVersion = "3.0.4"
  lazy val cassandraConnectorVersion = "2.0.6"
  lazy val typesafeConfigVersion = "1.3.2"


  //Spark dependencies
  val cassandraConnector = "com.datastax.spark" %% "spark-cassandra-connector" % cassandraConnectorVersion
  val sparkCore = "org.apache.spark" %% "spark-core" % sparkVersion
  val sparkSql = "org.apache.spark" %% "spark-sql" % sparkVersion

  // Testing libraries
  val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
  val holdenkarauSparkTesting = "com.holdenkarau" %% "spark-testing-base" % holdenkarauSparkTestingVersion
  val sparkHive = "org.apache.spark" %% "spark-hive" % sparkVersion
  val typesafeConfig = "com.typesafe" % "config" % typesafeConfigVersion

  val dependencies: Seq[ModuleID]  = Seq(cassandraConnector)
  val providedDependencies: Seq[ModuleID] = Seq(sparkCore, sparkSql).map(_ % "provided")
  val testDependencies: Seq[ModuleID] =
    Seq(scalaTest, holdenkarauSparkTesting, sparkHive, typesafeConfig).map(_ % "test")

}
