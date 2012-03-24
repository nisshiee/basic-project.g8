name := "$name$"

organization := "$organization$"

version := "$version$"

scalaVersion := "2.9.1"

libraryDependencies := Seq(
   "org.scalaz" %% "scalaz-core" % "6.0.4"
  ,"com.github.scala-incubator.io" %% "scala-io-core" % "0.3.0"
  ,"com.github.scala-incubator.io" %% "scala-io-file" % "0.3.0"
  ,"org.specs2" %% "specs2" % "1.7.1" % "test"
  ,"junit" % "junit" % "4.10" % "test"
  ,"org.pegdown" % "pegdown" % "1.1.0" % "test"
)

testOptions in (Test, test) += Tests.Argument("console", "html", "junitxml")

initialCommands := """
import scalaz._
import Scalaz._
import scalax.io._
import scalax.file._
import Path._
"""
