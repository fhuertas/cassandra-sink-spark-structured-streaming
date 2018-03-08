scalaVersion := "2.11.12"
crossScalaVersions := Seq("2.11.12", "2.10.7")

lazy val `cassandra_sink_220` = (project in file(".")).
  settings(Common.settings: _*).
  settings(name := Common.`name_2.2.0`)
