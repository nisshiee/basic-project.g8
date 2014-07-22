import AssemblyKeys._

net.virtualvoid.sbt.graph.Plugin.graphSettings

name := "$name$"

organization := "$organization$"

version := "$version$"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  // ---------- basic ----------
   "org.scalaz" %% "scalaz-core" % "7.0.6"
  ,"org.typelevel" %% "scalaz-contrib-210" % "0.1.5"
  ,"com.typesafe.akka" %% "akka-actor" % "2.3.4"
  ,"com.github.nscala-time" %% "nscala-time" % "1.2.0"
  // ---------- for FILE IO ----------
  ,"com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3"
  ,"com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3"
  ,"com.github.tototoshi" %% "scala-csv" % "1.0.0"
  // ---------- for WEB ----------
  ,"net.databinder.dispatch" %% "dispatch-core" % "0.11.1"
  ,"org.json4s" %% "json4s-jackson" % "3.2.10"
  // ---------- for CLI ----------
  ,"com.typesafe" % "config" % "1.2.1"
  ,"com.github.kxbmap" %% "configs" % "0.2.2"
  ,"com.github.scopt" %% "scopt" % "3.2.0"
  // ---------- for DB ----------
  ,"org.scalikejdbc" %% "scalikejdbc" % "2.0.5"
  ,"org.scalikejdbc" %% "scalikejdbc-interpolation" % "2.0.5"
  ,"org.scalikejdbc" %% "scalikejdbc-config" % "2.0.5"
  ,"mysql" % "mysql-connector-java" % "5.1.31"
  // ---------- for Logging ----------
  ,"org.slf4j" % "slf4j-api" % "1.7.7"
  ,"org.slf4j" % "slf4j-simple" % "1.7.7"
  // ---------- test scope ----------
  ,"org.specs2" %% "specs2" % "2.3.13" % "test"
  ,"org.typelevel" %% "scalaz-specs2" % "0.2" % "test"
  ,"junit" % "junit" % "4.11" % "test"
  ,"org.pegdown" % "pegdown" % "1.4.2" % "test"
)

scalacOptions <++= scalaVersion.map { sv =>
  if (sv.startsWith("2.1")) {
    Seq(
      "-deprecation",
      "-language:dynamics",
      "-language:postfixOps",
      "-language:reflectiveCalls",
      "-language:implicitConversions",
      "-language:higherKinds",
      "-language:existentials",
      "-language:reflectiveCalls",
      "-language:experimental.macros",
      "-Xfatal-warnings"
    )
  } else {
    Seq("-deprecation")
  }
}

testOptions in (Test, test) += Tests.Argument("console", "html", "junitxml")

initialCommands := """
import scalaz._, Scalaz._
import scalax.io._
import scalax.file._
import scalax.file.ImplicitConversions._
import com.github.nscala_time.time.Imports._
"""

cleanupCommands := ""


// ========== for sonatype oss publish ==========

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/nisshiee/$name$</url>
  <licenses>
    <license>
      <name>The MIT License (MIT)</name>
      <url>http://opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:nisshiee/$name$.git</url>
    <connection>scm:git:git@github.com:nisshiee/$name$.git</connection>
  </scm>
  <developers>
    <developer>
      <id>nisshiee</id>
      <name>Hirokazu Nishioka</name>
      <url>http://nisshiee.github.com/</url>
    </developer>
  </developers>)


// ========== for scaladoc ==========

// scalacOptions in (Compile, doc) <++= (baseDirectory in LocalProject("core")).map {

scalacOptions in (Compile, doc) <++= baseDirectory.map {
  bd => Seq("-sourcepath", bd.getAbsolutePath,
            "-doc-source-url", "https://github.com/nisshiee/$name$/blob/master€{FILE_PATH}.scala",
            "-implicits", "-diagrams")
}


// ========== for sbt-assembly ==========

seq(assemblySettings: _*)

jarName in assembly <<= (name, version) map { (name, version) => name + "-" + version + ".jar" }

// test in assembly := {}

mainClass in assembly := Some("App")

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case "application.conf" => MergeStrategy.concat
    case x => old(x)
  }
}

