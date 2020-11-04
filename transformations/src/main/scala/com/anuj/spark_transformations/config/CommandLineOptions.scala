package com.anuj.spark_transformations.config

import org.rogach.scallop.{ScallopConf, ScallopOption}

class CommandLineOptions(arguments: Seq[String]) extends ScallopConf(arguments){

  val inputFileName: ScallopOption[String] = opt[String](short = 'f', required = true)
  val inputFilePath: ScallopOption[String] = opt[String](short = 'p', required = true)
}

