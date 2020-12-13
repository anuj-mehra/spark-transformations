package com.anuj.spark_transformations.transactions

import com.anuj.spark_transformations.positions.Position

case class TranPosn(tranId: String, tranDate: String, assetName: String,quantity: String,tranType: String,
                    posnType: String, posnId: String, posnDate: String) {

}
