package com.baiyan.kafkaconnector

import java.util.Properties

import org.apache.flink.streaming.api.datastream.DataStream
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.fs.StringWriter
import org.apache.flink.streaming.connectors.fs.bucketing.BucketingSink
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08
import org.apache.flink.streaming.util.serialization.SimpleStringSchema

/*
从kafka读取消息写入hdfs
 */
object HdfsConnector {
  def main(args: Array[String]): Unit = {
    val env=StreamExecutionEnvironment.getExecutionEnvironment
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    // only required for Kafka 0.8
    properties.setProperty("zookeeper.connect", "localhost:2181")
    properties.setProperty("group.id", "test")
    val unit: DataStream[String] = env.addSource(new FlinkKafkaConsumer08[String]("test", new SimpleStringSchema(), properties))
    val counts: DataStream[(String, Int)] = unit.flatMap(x => x.split(" ")).map(x => (x, 1)).keyBy(0).sum(1)
    counts.print()

    val date: BucketingSink[(String,Int)] = new BucketingSink("hdfs://192.168.96.181:9000/date/")


    date.setWriter(new StringWriter())
    // this is 400 MB
    date.setBatchSize(1024 * 1024 * 400L)
    // this is 60 mins
    date.setBatchRolloverInterval(60 * 60 * 1024L)

    counts.addSink(date)

    env.execute("KafkaTest")
  }
}
