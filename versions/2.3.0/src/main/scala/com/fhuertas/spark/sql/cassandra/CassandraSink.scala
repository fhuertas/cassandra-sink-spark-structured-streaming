package com.fhuertas.spark.sql.cassandra

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.catalyst.{CatalystTypeConverters, InternalRow}
import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.{DataFrame, Row, SQLContext, SaveMode}

/** Provides and manages connections to Cassandra.
  *
  * A `CassandraSink` uses a `CassandraConnector` that is an instance is serializable and
  * can be safely sent over network,
  * because it automatically reestablishes the connection
  * to the same cluster after deserialization. Internally it saves
  * a list of all nodes in the cluster, so a connection can be established
  * even if the host given in the initial config is down.
  *
  * The provider of this sink is [[CassandraProvider]] and its configuration is the same
  * that a `CassandraConnector` that is configured from [[com.datastax.spark.connector.cql.CassandraConnectorConf]]
  * object which can be either given explicitly or automatically configured from [[org.apache.spark.SparkConf SparkConf]].
  * The connection options are:
  *   - `spark.cassandra.connection.host`:               contact points to connect to the Cassandra cluster, defaults to spark master host
  *   - `spark.cassandra.connection.port`:               Cassandra native port, defaults to 9042
  *   - `spark.cassandra.connection.factory`:            name of a Scala module or class implementing [[com.datastax.spark.connector.cql.CassandraConnectionFactory]] that allows to plugin custom code for connecting to Cassandra
  *   - `spark.cassandra.connection.keep_alive_ms`:      how long to keep unused connection before closing it (default 250 ms)
  *   - `spark.cassandra.connection.timeout_ms`:         how long to wait for connection to the Cassandra cluster (default 5 s)
  *   - `spark.cassandra.connection.reconnection_delay_ms.min`: initial delay determining how often to try to reconnect to a dead node (default 1 s)
  *   - `spark.cassandra.connection.reconnection_delay_ms.max`: final delay determining how often to try to reconnect to a dead node (default 60 s)
  *   - `spark.cassandra.auth.username`:                        login for password authentication
  *   - `spark.cassandra.auth.password`:                        password for password authentication
  *   - `spark.cassandra.auth.conf.factory`:                    name of a Scala module or class implementing [[com.datastax.spark.connector.cql.AuthConfFactory]] that allows to plugin custom authentication configuration
  *   - `spark.cassandra.query.retry.count`:                    how many times to reattempt a failed query (default 10)
  *   - `spark.cassandra.read.timeout_ms`:                      maximum period of time to wait for a read to return
  *   - `spark.cassandra.connection.ssl.enabled`:               enable secure connection to Cassandra cluster
  *   - `spark.cassandra.connection.ssl.trustStore.path`:      path for the trust store being used
  *   - `spark.cassandra.connection.ssl.trustStore.password`:  trust store password
  *   - `spark.cassandra.connection.ssl.trustStore.type`:      trust store type (default JKS)
  *   - `spark.cassandra.connection.ssl.protocol`:              SSL protocol (default TLS)
  *   - `spark.cassandra.connection.ssl.enabledAlgorithms`:         SSL cipher suites (default TLS_RSA_WITH_AES_128_CBC_SHA, TLS_RSA_WITH_AES_256_CBC_SHA)
  */
class CassandraSink(val context: SQLContext,
                    val parameters: Map[String, String],
                    val partitionColumns: Seq[String],
                    val outputMode: OutputMode) extends Sink {

  import CassandraSink._

  override def addBatch(batchId: Long, data: DataFrame): Unit = synchronized {
    val schema = data.schema
    val res = data.queryExecution.toRdd.mapPartitions { rows =>
      val converter = CatalystTypeConverters.createToScalaConverter(schema)
      rows.map(converter(_).asInstanceOf[Row])
    }

    val df = context.sparkSession.createDataFrame(res, schema)
    df.write
      .options(parameters)
      .format(CassandraOriginalSinkProvider)
      .mode(SaveMode.Append)
      .save()
  }
}

object CassandraSink {
  val CassandraOriginalSinkProvider = "org.apache.spark.sql.cassandra"
}