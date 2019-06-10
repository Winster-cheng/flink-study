package com.baiyan.class05.environment

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/*
  * @Auther: peilongcheng
  * @Date: 2019/6/10 18:59
  * @Description:获取flink环境的3种方法
  */
object GetExecutionEnvironment {
  def main(args: Array[String]): Unit = {
    //创建一个执行环境，表示当前执行程序的上下文。如果程序是独立调用的，则此方法返回本地执行环境；如果从命令行客户端调用程序以提交到集群，
    // 则此方法返回此集群的执行环境，也就是说，getExecutionEnvironment会根据查询运行的方式决定返回什么样的运行环境，是最常用的一种创建
    // 执行环境的方式。
    val env1 = StreamExecutionEnvironment.getExecutionEnvironment;

    //返回本地执行环境，需要在调用时指定默认的并行度。
    val env2 = StreamExecutionEnvironment.createLocalEnvironment(1)

    //返回集群执行环境，将Jar提交到远程服务器。需要在调用时指定JobManager的IP和端口号，并指定要在集群中运行的Jar包
    val env3 = StreamExecutionEnvironment.createRemoteEnvironment("localhost",1,"")
  }
}
