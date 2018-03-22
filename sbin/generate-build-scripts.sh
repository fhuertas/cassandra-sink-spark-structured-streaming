#!/usr/bin/env bash
SPARK_VERSION=${1:-"2.2.1"}
BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" ;pwd)"

if [[ "${SPARK_VERSION}" == "2.2.0" ]] || [[ "${SPARK_VERSION}" == "2.2.1" ]] ; then
    CASSANDRA_CONNECTOR_VERSION="2.0.7"
#elif [[ "${SPARK_VERSION}" == "2.2.1" ]]; then
#    CASSANDRA_CONNECTOR_VERSION="2.0.7"
else
    echo "Invalid Spark Version: $SPARK_VERSION"
    exit 1
fi

echo "Genrate library.sbt for Spark ${SPARK_VERSION}"

#cat
cat <<EOF > ${BASE_DIR}/../libraries.sbt
// Compiled
libraryDependencies+="com.datastax.spark" %% "spark-cassandra-connector" % "${CASSANDRA_CONNECTOR_VERSION}"
// Privided
libraryDependencies+="org.apache.spark" %% "spark-core" % "${SPARK_VERSION}" % "provided"
libraryDependencies+="org.apache.spark" %% "spark-sql" % "${SPARK_VERSION}" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "${SPARK_VERSION}" % "provided"
EOF

sed  -i -e "s/^name := \"cassandra-sink_.*/name := \"cassandra-sink_${SPARK_VERSION}\"/g" ${BASE_DIR}/../build.sbt