package com.anuj.spark_transformations.config

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

class ApplicationConfig(appConfigPath : String)  extends Serializable {

  lazy val config: Config = ConfigFactory.parseFile(new File(appConfigPath)).resolve()

  lazy val environment: String  = config.getString("env")

}
