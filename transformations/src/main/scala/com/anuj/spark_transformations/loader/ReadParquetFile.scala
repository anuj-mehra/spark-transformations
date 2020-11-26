package com.anuj.spark_transformations.loader

import com.anuj.spark_transformations.config.{ApplicationConfig, CommandLineOptions, SparkSessionConfig}
import org.apache.spark.sql.SparkSession

class ReadParquetFile {

  def main(args1: Array[String]): Unit = {

    val args = Seq("-f" , "input-csv-file.csv",
      "-p", "E:/tutorial/git/spark-transformations/transformations/src/main/resources",
      "-c", "application.conf")
    val jobName = "ReadTestFile"

    val commandLineInput = new CommandLineOptions(args)

    println(commandLineInput.configFilePath)

    val applicationConfig = new ApplicationConfig("/" + commandLineInput.configFilePath)
    val sparkSessionConfig = SparkSessionConfig(jobName, applicationConfig)
    implicit val sparkSession: SparkSession = sparkSessionConfig.get

    val obj = new ReadTextFileToDataFrame
    obj.process(commandLineInput, applicationConfig)

  }

}
