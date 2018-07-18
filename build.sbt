scalaVersion := "2.11.12"
name := "cassandra-sink_2.2.1"
organization := "com.fhuertas"
libraryDependencies ++= Dependencies.testDependencies

val sparkBaseVersion = "2.2"

scalaSource in Compile := baseDirectory.value / s"versions/$sparkBaseVersion/src/main/scala"
scalaSource in Test := baseDirectory.value / s"versions/$sparkBaseVersion/src/test/scala"
resourceDirectory in Compile := baseDirectory.value / s"versions/$sparkBaseVersion/src/main/resources"
resourceDirectory in Test := baseDirectory.value / s"versions/$sparkBaseVersion/src/test/resources"

addCommandAlias("ci-all",  ";+clean ;+compile ;+test ;+package")
addCommandAlias("release", ";+publishSigned ;sonatypeReleaseAll")



crossScalaVersions := Seq("2.11.12", "2.10.7")
