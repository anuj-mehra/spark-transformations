package com.anuj.spark_transformations.config

import org.rogach.scallop.{ScallopConf, ScallopOption}

class CommandLineOptions(arguments: Seq[String]) extends ScallopConf(arguments){

  val inputFileName: ScallopOption[String] = opt[String](short = 'f', required = true)
  val inputFileName2: ScallopOption[String] = opt[String](short = 'g', required = false)
  val inputFilePath: ScallopOption[String] = opt[String](short = 'p', required = true)
  val configFilePath: ScallopOption[String] = opt[String](short = 'c', required = true)

  verify()
}

