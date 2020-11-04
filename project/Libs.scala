import sbt._

object HadoopVersion {

  val Hadoop = "3.0.0"
  val HBase = "2.1.0"
  val Spark = "2.4.6"
}

object Libs {

  val scalaVersion = "2.11.8"
/*  val `scalatest` = "org.scalatest" %% "scalatest" % "3.0.4"  % "test"
  val `enumeration` = "com.beachane" %% "enumeratum" % "1.5.13"*/
  val `scallop` = "org.rogach" %% "scallop" % "3.5.1"
  val `scala-lang-library` = "org.scala-lang" % "scala-library" % scalaVersion
  val `scala-lang-compiler` = "org.scala-lang" % "scala-compiler" % scalaVersion
  val `scala-lang-reflect` = "org.scala-lang" % "scala-reflect" % scalaVersion
}

object Hadoop {

  val `hadoop-common` = "org.apache.hadoop" % "hadoop-common" % HadoopVersion.Hadoop
  val `hadoop-hdfs` = "org.apache.hadoop" % "hadoop-hdfs" % HadoopVersion.Hadoop
  val `hadoop-auth` = "org.apache.hadoop" % "hadoop-auth" % HadoopVersion.Hadoop
  val `hadoop-client` = "org.apache.hadoop" % "hadoop-client" % HadoopVersion.Hadoop
  val `hadoop-core` = "org.apache.hadoop" % "hadoop-core" % "1.2.1"
}

object Spark {

  val `spark-core` = "org.apache.spark"  %  "spark-core_2.11"  %  HadoopVersion.Spark excludeAll
    (ExclusionRule(organization = "xerces"), ExclusionRule(organization = "log4j"))

  val `spark-sql` = "org.apache.spark"  %  "spark-sql_2.11"  %  HadoopVersion.Spark

  val `commons-io` = "commons-io" % "commons-io" %  "2.6" exclude("commons-logging", "commons-logging") force()
  val `spark-avro-2.4` = "org.apache.spark" % "spark-avro_2.11" % "2.4.0"

  object TestLibs {

    val `spark-catalyst-test` = "org.apache.spark"  %  "spark-catalyst_2.11"  %  HadoopVersion.Spark % "test" classifier "tests"
    val `spark-core-test` = "org.apache.spark"  % "spark-core_2.11"  %  HadoopVersion.Spark % "test" classifier "tests"
    val `spark-sql-test` = "org.apache.spark"  %  "spark-sql_2.11"  %  HadoopVersion.Spark % "test" classifier "tests"
  }
}

object HBase {

  val `hbase-client` = "org.apache.hbase" % "hbase-client" % HadoopVersion.HBase
  val `hbase-server` = "org.apache.hbase" % "hbase-server" % HadoopVersion.HBase
  val `hbase-common` = "org.apache.hbase" % "hbase-common" % HadoopVersion.HBase
  val `hbase-protocol` = "org.apache.hbase" % "hbase-protocol" % HadoopVersion.HBase
  val `hbase-metrics` = "org.apache.hbase" % "hbase-metrics" % HadoopVersion.HBase
  val `hbase-metrics-api` = "org.apache.hbase" % "hbase-metrics-api" % HadoopVersion.HBase
  val `hbase-http` = "org.apache.hbase" % "hbase-http" % HadoopVersion.HBase
  val `hbase-mapreduce` = "org.apache.hbase" % "hbase-mapreduce" % HadoopVersion.HBase

}

object Logging{

  val `scala-logging` = "com.typesafe.scala-logging"  %% "scala-logging" % "3.7.2"
  val `logback` = "ch.qos.logback" % "logback-classic" % "1.2.3"
  val `logback-json-encoder` = "net.logstash.logback" % "logstash-logback-encoder" % "4.11" excludeAll ExclusionRule(
    organization = "com.fasterxml.jackson.core"
  )
}

object Common {
  val `typesafe-config` = "com.typesafe" % "config" % "1.3.2"
}

object Excluded {
  val `slf4j-log4j12` = "org.slf4j" % "slf4j-log4j12"
  /*val `log4j` = "log4j"  % "log4j"*/
}
