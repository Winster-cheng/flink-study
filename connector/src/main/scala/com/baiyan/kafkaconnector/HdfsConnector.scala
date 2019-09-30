package com.baiyan.kafkaconnector

import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink

/*
写入hdfs
 */
object HdfsConnector {
  def main(args: Array[String]): Unit = {
    val input: DataStream[String] = ["123","1234"];
    input.addSink(new BucketingSink[String]("/flink/hdfs-connector"))
  }
}
