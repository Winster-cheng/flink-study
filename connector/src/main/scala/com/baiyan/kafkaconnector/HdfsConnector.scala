package com.baiyan.kafkaconnector

import java.util.Properties

import org.apache.flink.streaming.api.scala.DataStream

import org.apache.flink.streaming.connectors.fs.StringWriter
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08
import org.apache.flink.streaming.util.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
/*
从kafka读取消息并且写入hdfs，默认writer就是StringWriter
 */
object HdfsConnector {
  def main(args: Array[String]): Unit = {
    val env=StreamExecutionEnvironment.getExecutionEnvironment
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    // only required for Kafka 0.8
    properties.setProperty("zookeeper.connect", "localhost:2181")
    properties.setProperty("group.id", "test")
    val unit: DataStream[String] = env.addSource(new FlinkKafkaConsumer08[String]("test1", new SimpleStringSchema(), properties))
    val counts: DataStream[(String, Int)] = unit.flatMap(x => x.split(" ")).map(x => (x, 1)).keyBy(0).sum(1)

    counts.print()
//桶的路径是唯一需要的方式
    val date: BucketingSink[(String,Int)] = new BucketingSink("hdfs://localhost:9000/flink")


    date.setWriter(new StringWriter())
//    // this is 400 MB
//    date.setBatchSize(1024 * 1024 * 400L)
//    // this is 10 seconds
//    date.setBatchRolloverInterval(10*1000L)
//    date.setInactiveBucketCheckInterval(1);
//    date.setInactiveBucketThreshold
    counts.addSink(date)

    env.execute("KafkaTest")
  }
}
