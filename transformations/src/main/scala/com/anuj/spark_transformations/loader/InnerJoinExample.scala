package com.anuj.spark_transformations.loader

import com.anuj.spark_transformations.config.{ApplicationConfig, CommandLineOptions, SparkSessionConfig}
import com.anuj.spark_transformations.positions.Position
import com.anuj.spark_transformations.transactions.Transaction
import org.apache.spark.sql._

object InnerJoinExample {

  def main(args: Array[String]): Unit = {
    val args = Seq("-f", "input-tran-txt-file.txt", "-g", "input-posn-txt-file.txt",
      "-p", "E:/tutorial/git/spark-transformations/transformations/src/main/resources",
      "-c", "application.conf")
    val jobName = "ReadTextFile"

    val commandLineInput = new CommandLineOptions(args)

    val applicationConfig = new ApplicationConfig("/" + commandLineInput.configFilePath)
    val sparkSessionConfig = SparkSessionConfig(jobName, applicationConfig)
    implicit val sparkSession: SparkSession = sparkSessionConfig.get

    val obj = new InnerJoinExample
    obj.process(commandLineInput, applicationConfig)
  }
}

class InnerJoinExample extends  Serializable{

  def process(commandLineInput: CommandLineOptions,
              applicationConfig: ApplicationConfig)(implicit spark: SparkSession): Unit = {


    import spark.implicits._

    val filePath: Option[String] = commandLineInput.inputFilePath.toOption
    val tranFileName: Option[String] = commandLineInput.inputFileName.toOption
    val posnFileName: Option[String] = commandLineInput.inputFileName2.toOption


    implicit def transactionEncoder: org.apache.spark.sql.Encoder[Transaction] =
      org.apache.spark.sql.Encoders.kryo[Transaction]

    implicit def rowEncoder: org.apache.spark.sql.Encoder[Row] =
      org.apache.spark.sql.Encoders.kryo[Row]

    val tranEncoder = Encoders.product[Transaction]
    val tranDf: Dataset[Transaction] = spark.read.text(filePath.get.concat("/").concat(tranFileName.get))
      .map((row) => row.toString().split(","))
      .map(attributes => {

        Transaction(attributes(0), attributes(1), attributes(2), attributes(3), attributes(4))
      })(tranEncoder)


    val posnEncoder = Encoders.product[Position]
    val posnDf: Dataset[Position] = spark.read.text(filePath.get.concat("/").concat(posnFileName.get))
      .map((row) => row.toString().split(","))
      .map(attributes => {

        Position(attributes(0), attributes(1), attributes(2), attributes(3), attributes(4))
      })(posnEncoder)

    posnDf.show(false)

    // Inner join
    val innerJoinDf: DataFrame = tranDf.join(posnDf, Seq("assetName","quantity"))
    innerJoinDf.printSchema()

    innerJoinDf.show(false)

  }
}
