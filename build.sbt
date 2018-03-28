scalaVersion := "2.11.12"
crossScalaVersions := Seq("2.11.12", "2.10.7")
name := "cassandra-sink_2.2.1"
organization := "com.fhuertas"
libraryDependencies ++= Dependencies.testDependencies

addCommandAlias("ci-all",  ";+clean ;+compile ;+test ;+package")
addCommandAlias("release", ";+publishSigned ;sonatypeReleaseAll")
