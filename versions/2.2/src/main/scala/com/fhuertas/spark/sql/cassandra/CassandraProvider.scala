package com.fhuertas.spark.sql.cassandra

import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.execution.streaming.Sink
import org.apache.spark.sql.sources.StreamSinkProvider
import org.apache.spark.sql.streaming.OutputMode

class CassandraProvider extends StreamSinkProvider {
  override def createSink(context: SQLContext,
                          parameters: Map[String, String],
                          partitionColumns: Seq[String],
                          outputMode: OutputMode): Sink =
    new CassandraSink(context, parameters, partitionColumns, outputMode)
}
