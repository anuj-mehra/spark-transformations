package com.anuj.spark_transformations.loader


import com.anuj.spark_transformations.config.{ApplicationConfig, CommandLineOptions, SparkSessionConfig}
import org.apache.spark.sql.{DataFrame, SparkSession}

object ReadTestFile {

  def main(args1: Array[String]): Unit = {

    val args = Seq("-f" , "input-csv-file.csv",
      "-p", "E:/tutorial/git/spark-transformations/transformations/src/main/resources")
    val jobName = "ReadTestFile"

    val applicationConfig = new ApplicationConfig("/application.conf")
    val sparkSessionConfig = SparkSessionConfig(jobName, applicationConfig)
    implicit val sparkSession: SparkSession = sparkSessionConfig.get

    val commandLineInput = new CommandLineOptions(args)

    val obj = new ReadTestFile
    obj.process(commandLineInput, applicationConfig)

  }
}

class ReadTestFile extends Serializable {

  def process(commandLineInput: CommandLineOptions,
              applicationConfig: ApplicationConfig)(implicit sparkSession:SparkSession): Unit = {

    val filePath: Option[String] = commandLineInput.inputFilePath.toOption
    val fileName: Option[String] = commandLineInput.inputFileName.toOption
    val inputDf: DataFrame = sparkSession.read.csv(filePath.get.concat("/").concat(fileName.get))

    println("--------------->" + inputDf.count())
  }
}
