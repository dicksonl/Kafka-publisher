//import sbtdocker.DockerPlugin
//import sbtdocker.immutable.Dockerfile

name := "DataPush"

fork in run := true

organization := "dickson"

version := "1.0"

scalaVersion := "2.12.2"

scalaSource in Compile <<= baseDirectory(_ / "src")

mainClass in (Compile, run) := Some("main.MainApp")

mainClass in (Compile, packageBin) := Some("main.MainApp")

javaOptions in run ++= Seq("-Xms4G", "-Xmx4G", "-XX:+UseConcMarkSweepGC") //use 8G in production

//enablePlugins(DockerPlugin)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-compiler" % "2.12.2",
  "org.scala-lang" % "scala-reflect" % "2.12.2",
  "org.scala-lang.modules" % "scala-parser-combinators_2.11" % "1.0.6",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.2.0",
  "com.github.nscala-time" % "nscala-time_2.11" % "2.16.0",
  "org.apache.spark" % "spark-core_2.10" % "2.1.1",
  "org.apache.spark" % "spark-sql_2.10" % "2.1.1",
  "com.datastax.spark" % "spark-cassandra-connector_2.10" % "2.0.2",
  "com.microsoft.sqlserver" % "mssql-jdbc" % "6.1.0.jre8",
  "org.apache.kafka" % "kafka-clients" % "0.10.2.1",
  "com.google.code.gson" % "gson" % "2.8.1"
)
