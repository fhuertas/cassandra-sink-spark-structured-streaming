publishMavenStyle := true

pomIncludeRepository := { _ => false }


useGpg := false
usePgpKeyHex("F44BF62EA9AF85E6")
pgpPublicRing := baseDirectory.value / "project" / ".gnupg" / "pubring.gpg"
pgpSecretRing := baseDirectory.value / "project" / ".gnupg" / "secring.gpg"
pgpPassphrase := sys.env.get("PGP_PASS").map(_.toArray)


isSnapshot := version.value endsWith "SNAPSHOT"

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

licenses := Seq("Apache License, Version 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/fhuertas/cassandra-sink-spark-structured-streaming"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/fhuertas/cassandra-sink-spark-structured-streaming.git"),
    "scm:git@github.com:fhuertas/cassandra-sink-spark-structured-streaming.git"
  ))

developers := List(
  Developer(
    id="fhuertas",
    name="Francisco Huertas",
    email="francisco@fhuertas.com",
    url=url("https://www.fhuertas.com")
  ))

//pomExtra := (
//  <url></url>
//    <licenses>
//      <license>
//        <name></name>
//        <url></url>
//        <distribution>repo</distribution>
//      </license>
//    </licenses>
//    <scm>
//      <url>git@github.com:</url>
//      <connection></connection>
//    </scm>
//    <developers>
//      <developer>
//        <id></id>
//        <name></name>
//        <url></url>
//      </developer>
//    </developers>)
