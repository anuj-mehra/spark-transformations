package com.anuj.spark_transformations.loader

import com.anuj.spark_transformations.config.{ApplicationConfig, CommandLineOptions, SparkSessionConfig}
import org.apache.spark.sql._
import org.apache.spark.sql.catalyst.encoders.RowEncoder
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.functions.{col, explode, udf}

object SelfJoinExample {

  def main(args: Array[String]): Unit = {
    val args = Seq("-f", "employee-data.txt",
      "-p", "E:/tutorial/git/spark-transformations/transformations/src/main/resources",
      "-c", "application.conf")
    val jobName = "ReadTextFile"

    val commandLineInput = new CommandLineOptions(args)

    val applicationConfig = new ApplicationConfig("/" + commandLineInput.configFilePath)
    val sparkSessionConfig = SparkSessionConfig(jobName, applicationConfig)
    implicit val sparkSession: SparkSession = sparkSessionConfig.get

    val obj = new SelfJoinExample
    obj.process(commandLineInput, applicationConfig)
  }
}

class SelfJoinExample extends  Serializable{

  def process(commandLineInput: CommandLineOptions,
              applicationConfig: ApplicationConfig)(implicit spark: SparkSession): Unit = {


    import spark.implicits._

    val filePath: Option[String] = commandLineInput.inputFilePath.toOption
    val empDataFileName: Option[String] = commandLineInput.inputFileName.toOption

    val inputSchema: StructType = StructType(
      List(
        StructField("emp_id", StringType, true),
        StructField("first_name", StringType, true),
        StructField("last_name", StringType, true),
        StructField("superior_emp_id", StringType, true),
        StructField("emp_dept_id", StringType, true),
        StructField("salary", StringType, true)
      )
    )

    val encoder = RowEncoder(inputSchema)

    // read text file and skip reading header
    val empDf: DataFrame = spark.read.text(filePath.get.concat("/").concat(empDataFileName.get))
      .map((row) => row.toString().split(","))
      .map(attributes => {

        Row(attributes(0), attributes(1), attributes(2), attributes(3), attributes(4), attributes(5))
      })(encoder)

    // Self join
    val selfJoinDf = empDf.alias("emp1").join(empDf.alias("emp2"),
      col("emp1.superior_emp_id") === col("emp2.emp_id"), "inner")
      .select(col("emp1.emp_id"),col("emp1.first_name"),col("emp1.last_name"),
        col("emp2.emp_id").alias("superior_emp_id"),
        col("emp2.name").alias("superior_emp_name"))

    selfJoinDf.printSchema()
    selfJoinDf.show(false)

  }
}
