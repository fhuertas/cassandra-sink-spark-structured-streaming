#!/usr/bin/env bash
SPARK_VERSION=${1:-"2.3.0"}
BASE_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" ;pwd)"
SCALA_10_VERSION="2.10.7"
SCALA_11_VERSION="2.11.12"
SCALA_VERSION=$SCALA_11_VERSION

if [[ "${SPARK_VERSION}" == "2.2.0" ]] || [[ "${SPARK_VERSION}" == "2.2.1" ]] ; then
    CASSANDRA_CONNECTOR_VERSION="2.0.7"
    CROSS_SCALA_VERSION="crossScalaVersions := Seq(\"${SCALA_11_VERSION}\", \"${SCALA_10_VERSION}\")"
elif [[ "${SPARK_VERSION}" == "2.3.0" ]]; then
    CASSANDRA_CONNECTOR_VERSION="2.3.0"
    CROSS_SCALA_VERSION=''
else
    echo "Invalid Spark Version: $SPARK_VERSION"
    exit 1
fi

echo "Generate library.sbt for Spark ${SPARK_VERSION}"

#cat
cat <<EOF > ${BASE_DIR}/../libraries.sbt
// Compiled
libraryDependencies+="com.datastax.spark" %% "spark-cassandra-connector" % "${CASSANDRA_CONNECTOR_VERSION}"
// Privided
libraryDependencies+="org.apache.spark" %% "spark-core" % "${SPARK_VERSION}" % "provided"
libraryDependencies+="org.apache.spark" %% "spark-sql" % "${SPARK_VERSION}" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "${SPARK_VERSION}" % "provided"
EOF

echo "Regenerate build.sbt"
# Remove cross scala version line to put the new libraryDependencies
sed -i "/^crossScalaVersions*/d" ${BASE_DIR}/../build.sbt
echo ${CROSS_SCALA_VERSION} >> ${BASE_DIR}/../build.sbt

# Scala version
sed -i -e "s/^scalaVersion.*/scalaVersion := \"${SCALA_VERSION}\"/g" ${BASE_DIR}/../build.sbt

# Package name
sed -i -e "s/^name := \"cassandra-sink_.*/name := \"cassandra-sink_${SPARK_VERSION}\"/g" ${BASE_DIR}/../build.sbt
