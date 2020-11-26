package com.anuj.spark_transformations.loader

import com.anuj.spark_transformations.config.{ApplicationConfig, CommandLineOptions, SparkSessionConfig}
import com.anuj.spark_transformations.positions.Position
import com.anuj.spark_transformations.transactions.Transaction
import org.apache.spark.sql._

object JoinTwoDatasets {

  def main(args1: Array[String]): Unit = {

    val args = Seq("-f", "input-tran-txt-file.txt",
      "-p", "E:/tutorial/git/spark-transformations/transformations/src/main/resources",
      "-c", "application.conf")
    val jobName = "ReadTextFile"

    val commandLineInput = new CommandLineOptions(args)

    println(commandLineInput.configFilePath)

    val applicationConfig = new ApplicationConfig("/" + commandLineInput.configFilePath)
    val sparkSessionConfig = SparkSessionConfig(jobName, applicationConfig)
    implicit val sparkSession: SparkSession = sparkSessionConfig.get

    val obj = new JoinTwoDatasets
    obj.process(commandLineInput, applicationConfig)

  }
}

class JoinTwoDatasets extends Serializable {

  def process(commandLineInput: CommandLineOptions,
              applicationConfig: ApplicationConfig)(implicit spark: SparkSession): Unit = {

    import spark.implicits._

    val filePath: Option[String] = commandLineInput.inputFilePath.toOption
    val fileName: Option[String] = commandLineInput.inputFileName.toOption

    import java.text.SimpleDateFormat
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy")

    implicit def transactionEncoder: org.apache.spark.sql.Encoder[Transaction] =
      org.apache.spark.sql.Encoders.kryo[Transaction]

    implicit def rowEncoder: org.apache.spark.sql.Encoder[Row] =
      org.apache.spark.sql.Encoders.kryo[Row]

    val tranEncoder = Encoders.product[Transaction]

    val df1: Dataset[Transaction] = spark.read.text(filePath.get.concat("/").concat(fileName.get))
      .map((row) => row.toString().split(","))
      .map(attributes => {

        Transaction(attributes(0), attributes(1), attributes(2), attributes(3), attributes(4))
      })(tranEncoder)

    df1.show(false)

    val posnEncoder = Encoders.product[Position]
    val df2: Dataset[Position] = spark.read.text(filePath.get.concat("/").concat(fileName.get))
      .map((row) => row.toString().split(","))
      .map(attributes => {

        Position(attributes(0), attributes(1), attributes(2), attributes(3), attributes(4))
      })(posnEncoder)

    df2.show(false)

    val dfJoined1: DataFrame = df1.join(df2, "assetName")
    dfJoined1.show(false)
    dfJoined1.printSchema()
    println(dfJoined1.count())


    val dfJoined2: Dataset[(Transaction, Position)] =
      df1.joinWith(df2, df1("assetName") ===  df2("assetName"),
        "inner")
    dfJoined2.show(false)
    dfJoined2.printSchema()
    println(dfJoined2.count())

  }
}
