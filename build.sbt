scalaVersion := "2.11.12"
name := "cassandra-sink_2.3.0"
organization := "com.fhuertas"
libraryDependencies ++= Dependencies.testDependencies

addCommandAlias("ci-all",  ";+clean ;+compile ;+test ;+package")
addCommandAlias("release", ";+publishSigned ;sonatypeReleaseAll")


