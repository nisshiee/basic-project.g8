import scalariform.formatter.preferences._
import ScalariformKeys._

val scalaV = "2.11.5"
val scalazV = "7.1.1"
val akkaV = "2.3.9"
val scalikejdbcV = "2.2.3"
val scalaioV = "0.4.3"
val slf4jV = "1.7.10"
val specs2V = "2.4.16"

lazy val root = (project in file("."))

// ========== plugin settings ==========
scalariformSettings
doctestSettings

// ========== basics ==========
name := "$name$"
organization := "$organization$"
version := "$version$"
scalaVersion := scalaV

// ========== dependencies ==========
libraryDependencies ++= Seq (
  // ---------- basic ----------
   "org.scalaz" %% "scalaz-core" % scalazV
  ,"com.typesafe.akka" %% "akka-actor" % akkaV
  ,"com.github.nscala-time" %% "nscala-time" % "1.8.0"
  // ---------- for FILE IO ----------
  ,"com.github.scala-incubator.io" %% "scala-io-core" % scalaioV
  ,"com.github.scala-incubator.io" %% "scala-io-file" % scalaioV
  ,"com.github.tototoshi" %% "scala-csv" % "1.1.2"
  // ---------- for WEB ----------
  ,"net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
  ,"org.json4s" %% "json4s-jackson" % "3.2.11"
  // ---------- for CLI ----------
  ,"com.typesafe" % "config" % "1.2.1"
  ,"com.github.kxbmap" %% "configs" % "0.2.3"
  ,"com.github.scopt" %% "scopt" % "3.3.0"
  // ---------- for DB ----------
  ,"org.scalikejdbc" %% "scalikejdbc" % scalikejdbcV
  ,"org.scalikejdbc" %% "scalikejdbc-interpolation" % scalikejdbcV
  ,"org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcV
  ,"mysql" % "mysql-connector-java" % "5.1.34"
  // ---------- for Logging ----------
  ,"org.slf4j" % "slf4j-api" % slf4jV
  ,"org.slf4j" % "slf4j-simple" % slf4jV
  // ---------- test scope ----------
  ,"org.specs2" %% "specs2-core" % specs2V % "test"
  ,"org.specs2" %% "specs2-scalacheck" % specs2V % "test"
  ,"org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
  ,"org.typelevel" %% "scalaz-specs2" % "0.3.0" % "test"
  ,"org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcV % "test"
  ,"junit" % "junit" % "4.12" % "test"
  ,"org.pegdown" % "pegdown" % "1.4.2" % "test"
)

// ========== other options ==========
scalacOptions ++= {
  if (scalaVersion.value startsWith "2.1") Seq (
     "-deprecation"
    ,"-language:dynamics"
    ,"-language:postfixOps"
    ,"-language:reflectiveCalls"
    ,"-language:implicitConversions"
    ,"-language:higherKinds"
    ,"-language:existentials"
    ,"-language:reflectiveCalls"
    ,"-language:experimental.macros"
    ,"-Xfatal-warnings"
  ) else Seq (
    "-deprecation"
  )
}

testOptions in (Test, test) += Tests.Argument("console", "html", "junitxml")

initialCommands :=
  """
    |import scalaz._, Scalaz._
    |import scalax.io._
    |import scalax.file._
    |import scalax.file.ImplicitConversions._
    |import com.github.nscala_time.time.Imports._
  """.stripMargin

cleanupCommands := ""

// ========== for sbt-doctest ==========
doctestTestFramework := DoctestTestFramework.Specs2
doctestWithDependencies := false

// ========== for scalariform ==========
ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(CompactControlReadability, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)

// ========== for sonatype oss publish ==========
publishMavenStyle := true
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value endsWith "SNAPSHOT")
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
publishArtifact in Test := false
pomIncludeRepository := { _ => false}
pomExtra :=
  <url>https://github.com/nisshiee/$name$</url>
  <licenses>
    <license>
      <name>The MIT License (MIT)</name>
      <url>http://opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:nisshiee/$name$</url>
    <connection>scm:git:git@github.com:nisshiee/$name$.git</connection>
  </scm>
  <developers>
    <developer>
      <id>nisshiee</id>
      <name>Hirokazu Nishioka</name>
      <url>http://nisshiee.github.com/</url>
    </developer>
  </developers>

// ========== for scaladoc ==========
scalacOptions in (Compile, doc) ++= Seq(
   "-sourcepath", baseDirectory.value.getAbsolutePath // (baseDirectory in LocalProject("core"))
  ,"-doc-source-url", "https://github.com/nisshiee/$name$/blob/" + version.value + "€{FILE_PATH}.scala"
  ,"-implicits"
  ,"-diagrams"
)

// ========== for sbt-assembly ==========
assemblyJarName in assembly := name.value + "-" + version.value + ".jar"
mainClass in assembly := Some("App")
// test in assembly := {}
assemblyMergeStrategy in assembly := {
  case "application.conf" => MergeStrategy.concat
  case x => (mergeStrategy in assembly).value(x)
}
