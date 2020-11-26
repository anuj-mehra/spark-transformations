package com.anuj.spark_transformations.loader

import com.anuj.spark_transformations.config.{ApplicationConfig, CommandLineOptions, SparkSessionConfig}
import org.apache.spark.sql.{DataFrame, SparkSession}

object ReadCSVFile {

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

class ReadCSVFile extends Serializable {

  def process(commandLineInput: CommandLineOptions,
              applicationConfig: ApplicationConfig)(implicit sparkSession:SparkSession): Unit = {

    val filePath: Option[String] = commandLineInput.inputFilePath.toOption
    val fileName: Option[String] = commandLineInput.inputFileName.toOption
    val inputDf: DataFrame = sparkSession.read.csv(filePath.get.concat("/").concat(fileName.get))

    println("--------------->" + inputDf.count())
  }
}