package com.fhuertas.spark.sql.cassandra

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.sql.execution.streaming.MemoryStream
import org.scalacheck.Gen
import org.scalatest.{FlatSpec, Matchers}

class CassandraSinkTest extends FlatSpec with Matchers {
  import CassandraSinkTest._

  lazy val configuration: Config = ConfigFactory.load()

  lazy val CassandraOpt: Map[String, String] = loadAsMap(configuration, "test.cassandra") + ("checkpointLocation" -> s"target/checkpoint-${System.currentTimeMillis}")
  lazy val awaitTime: Long = configuration.getLong("test.awaitTermination")

  implicit lazy val spark: SparkSession = SparkSession.builder
    .master("local")
    .appName("CassandraConnectorTest")
    .getOrCreate()
  implicit lazy val ctx: SQLContext = spark.sqlContext


  it should "store a stream data set in cassandra for spark 2.3.1" in {
    import spark.implicits._

    // Prepare data
    val data = Gen.listOfN(Gen.choose(10, 100).sample.get, genTuple(genWord, genWord)).sample.get.toMap.toSeq
    val keys = data.map(_._1).toSet
    val inputStream = MemoryStream[(String, String)]

    // First element of the table is the key
    val query = inputStream.toDS.toDF("first","second").writeStream
      .format("com.fhuertas.spark.sql.cassandra.CassandraProvider").outputMode("append")
      .options(CassandraOpt).start()
    inputStream.addData(data)
    query.processAllAvailable()

    // Collect result
    val result = spark.read.format("org.apache.spark.sql.cassandra").options(CassandraOpt)
      .load().as[(String, String)].collect().filter(row => keys.contains(row._1))
    result.toSet shouldBe data.toSet
  }
}

object CassandraSinkTest {
  val genWord: Gen[String] = Gen.oneOf(Seq("wrestle", "husky", "cactus", "slip", "appreciate", "multiply", "doubtful",
    "share", "deep", "waste", "tiger", "smart", "educate", "heavy", "crayon", "pathetic", "connection", "identify",
    "repulsive", "sticks", "longing", "visit", "condemned", "wise", "ghost", "luxuriant", "ants", "resonant", "natural",
    "short", "adamant", "melt", "disillusioned", "youthful", "muddle", "brief", "pushy", "orange", "wing", "coast",
    "whimsical", "icky", "cycle", "instruct", "functional", "woebegone", "appear", "north", "fabulous", "aboriginal",
    "outgoing", "reflective", "fear", "guttural", "market", "familiar", "kiss", "instrument", "abrupt", "woman", "high",
    "flaky", "productive", "defeated", "somber", "ray", "sheep", "include", "tow", "kindhearted", "exciting", "lush",
    "pinch", "window", "ignore", "juggle", "wobble", "smell", "step", "grandfather", "key", "garrulous", "special",
    "yielding", "talk", "dreary", "tall", "foregoing", "kick", "amuck", "utter", "flame", "prose", "abashed", "square",
    "hot", "fly", "continue", "cry", "spooky", "Shadingo"))

  def genTuple[T, R](genT: Gen[T], genR: Gen[R]): Gen[(T, R)] = for {
    t <- genT
    r <- genR
  } yield (t, r)

  def loadAsMap(config: Config, ns: String): Map[String, String] = {
    import scala.collection.JavaConversions._
    config.getConfig(ns).entrySet().map(entry =>
      entry.getKey -> entry.getValue.unwrapped().toString).toMap
  }
}