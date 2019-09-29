#### 参考文档：https://ci.apache.org/projects/flink/flink-docs-release-1.6/dev/connectors/filesystem_sink.html

##1. kafka connector
flink提供了特殊的kafka连接器，用于从kafka主题读写数据。

flink-kafka使用者与flink的检查点机制集成，以提供精确的一次处理语义。

为了实现这一点，Flink并不完全依赖于Kafka的消费者群体偏移量跟踪，而是在内部跟踪和检查这些偏移量。

####1.1 添加maven
```
<dependency>
  <groupId>org.apache.flink</groupId>
  <artifactId>flink-connector-kafka-0.8_2.11</artifactId>
  <version>1.6.1</version>
</dependency>

```
#### 1.2 安装kafka
请自行百度

#### 1.3




#### 说明：
- 1.flink connectors指的是Streaming connectors

- 2.该版本为1.6版本，flink已经发布了1.9版本。
