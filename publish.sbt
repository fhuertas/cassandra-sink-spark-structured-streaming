publishMavenStyle := true

pomIncludeRepository := { _ => false }


useGpg := false
usePgpKeyHex("2673B174C4071B0E")
pgpPublicRing := baseDirectory.value / "project" / ".gnupg" / "pubring.gpg"
pgpSecretRing := baseDirectory.value / "project" / ".gnupg" / "secring.gpg"
pgpPassphrase := sys.env.get("PGP_PASS").map(_.toArray)


publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomExtra :=
  <url>https://github.com/fhuertas/cassandra-sink-spark-structured-streaming</url>
    <licenses>
      <license>
        <name>Apache License, Version 2.0</name>
        <url>https://www.apache.org/licenses/LICENSE-2.0.txt</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:fhuertas/cassandra-sink-spark-structured-streaming.git</url>
      <connection>scm:git@github.com:fhuertas/cassandra-sink-spark-structured-streaming.git</connection>
    </scm>
    <developers>
      <developer>
        <id>fhuertas</id>
        <name>Francisco Huertas</name>
        <url>http://www.fhuertas.com</url>
      </developer>
    </developers>
