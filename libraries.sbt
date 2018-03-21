// Compiled
libraryDependencies+="com.datastax.spark" %% "spark-cassandra-connector" % "2.0.7"
// Privided
libraryDependencies+="org.apache.spark" %% "spark-core" % "2.2.1" % "provided"
libraryDependencies+="org.apache.spark" %% "spark-sql" % "2.2.1" % "provided"
//Test
libraryDependencies+="com.holdenkarau" %% "spark-testing-base" % "2.2.1_0.9.0" % "test"
libraryDependencies+="org.apache.spark" %% "spark-hive" % "2.2.1" % "test"
