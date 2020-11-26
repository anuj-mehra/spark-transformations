package com.anuj.spark_transformations.transactions

object TranType extends Enumeration with Serializable {
  type TranType = Value

  val BUY: TranType.Value = Value
  val SELL: TranType.Value = Value
}

case class Transaction(tranId: String, tranDate: String, assetName: String,quantity: String,tranType: String)
