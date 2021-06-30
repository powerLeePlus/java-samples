# spring cloud stream + rocketmq

## rocketmq服务
- 启动 Name Server
```sh bin/mqnamesrv```
- 启动 Broker
```sh bin/mqbroker -n localhost:9876```
- 创建 Topic: test-topic
```sh bin/mqadmin updateTopic -n localhost:9876 -c DefaultCluster -t test-topic```

## rocketmq生成者和消费者，不同的发送方式及不同的消费方式

## rocketmq发送事务消息

## 关键词
- binding
- source(output)
- sink(input)
- MessageChannel
- SubscribableChannel | PollableMessageSource
- StreamListener
- RocketMQLocalTransactionListener


