# sink-cassandra-structured-streaming

[![Build Status](https://travis-ci.org/fhuertas/cassandra-sink-spark-structured-streaming.svg?branch=master)](https://travis-ci.org/fhuertas/cassandra-sink-spark-structured-streaming)
[![Coverage Status](https://coveralls.io/repos/github/fhuertas/cassandra-sink-spark-structured-streaming/badge.svg?branch=multibuild)](https://coveralls.io/github/fhuertas/cassandra-sink-spark-structured-streaming?branch=multibuild)

Unoffical sink for cassandra for spark structured streaming. This connector only support **append** mode

## Usage

First, you should include the dependency in your code.

*In progress*

This connector is used like others structured streaming connectors. This is a code example
that how to use the connectors
```Scala
dataframe.writeStream.options(...).start()
```

You can see an example in the test: *CassandraSinkTest*
