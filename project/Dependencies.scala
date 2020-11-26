import sbt.librarymanagement.ModuleID

object Dependencies {

  var SparkModule: scala.collection.mutable.Seq[ModuleID] = scala.collection.mutable.Seq(
    Spark.`spark-core`,
    Spark.`spark-sql`,
    Spark.`spark-avro-2.4`,
    Spark.`commons-io`,
    Spark.TestLibs.`spark-catalyst-test`,
    Spark.TestLibs.`spark-core-test`,
    Spark.TestLibs.`spark-sql-test`,
   /* HBase.`hbase-client`,
    HBase.`hbase-common`,
    HBase.`hbase-http`,
    HBase.`hbase-metrics-api`,
    HBase.`hbase-metrics`,
    HBase.`hbase-protocol`,
    HBase.`hbase-server`,
    HBase.`hbase-mapreduce`,*/
  /*  Hadoop.`hadoop-auth`,
    Hadoop.`hadoop-client`,
    Hadoop.`hadoop-common`,
  */ // Hadoop.`hadoop-core`,
    //Hadoop.`hadoop-hdfs`,
    Common.`typesafe-config`,
    //Libs.`enumeration`,
    Libs.`scallop`,
    Libs.`scala-lang-library`,
    Libs.`scala-lang-compiler`,
    Libs.`scala-lang-reflect`,
    Excluded.`log4j`
  )

  val logging = Seq(Logging.`scala-logging`, Logging.`logback`, Logging.`logback-json-encoder`)

  val excludedJackson: scala.collection.mutable.Seq[ModuleID] = scala.collection.mutable.Seq(
    Excluded.`fasterxml.jackson.core1`,
    Excluded.`fasterxml.jackson.core2`,
    Excluded.`fasterxml.jackson.core3`,
  )

  val excluded = Seq(Excluded.`slf4j-log4j12`)
}
