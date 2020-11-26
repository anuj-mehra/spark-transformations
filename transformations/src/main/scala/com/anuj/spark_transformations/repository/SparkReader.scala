package com.anuj.spark_transformations.repository

import com.anuj.spark_transformations.repository.FileType.FileType

object FileType extends Enumeration {

  type FileType = Value

  val TEXT: FileType.Value = Value
  val CSV: FileType.Value = Value
  val AVRO: FileType.Value = Value
  val PARQUET: FileType.Value = Value
}

class SparkReader(fullqualifiedPath: String, fileType: FileType) {


}
