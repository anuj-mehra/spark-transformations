package com.anuj.spark_transformations.config

import org.apache.spark.sql.SparkSession

class SparkSessionConfig(jobName: String,appConfig: ApplicationConfig) {

  def get: SparkSession = {
    val sparkSessionBuilder: SparkSession.Builder = SparkSession
      .builder
      .appName(jobName)

    sparkSessionBuilder.master("local[*]")
/*
    appConfig.environment match {
      case "dev" =>
        sparkSessionBuilder.master("local[*]")
      case "_" =>
    }*/

    sparkSessionBuilder.config("spark.driver.maxResultSize",0)
    sparkSessionBuilder.config("spark.sql.broadcastTimeout","36000")
    sparkSessionBuilder.config("spark.sql.autoBroadcastJoinThreshold",-1)

    sparkSessionBuilder.getOrCreate()
  }
}

object SparkSessionConfig {

  def apply(jobName: String, appConfig: ApplicationConfig) = new SparkSessionConfig(jobName,appConfig)
}