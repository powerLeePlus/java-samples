# 高级特性之mandatory、immediate、ReturnListener

上节介绍了RabbitMQ的生产者确认机制，里面特别强调过：当消息发送到交换机后，如果该交换机没有绑定队列或者交换机上没有与发送消息的routing key相匹配的队列，那么消息也会丢失，并且生产者都会收到RabbitMQ投递成功的消息（事务机制会返回Tx.Commit-OK，confirm机制会返回Basic.Ack）。
这个时候生产者并不知道自己发送的消息丢失了，生产者需要知道自己发送的消息是否路由到了队列里，关于这点，RabbitMQ提供了mandatory和ReturnListener。
另外，RabbitMQ 还提供了另一种解决方案——备份交换器(Altemate Exchange) 可以将未能被交换器路由的消息（没有绑定队列或者没有匹配的绑定）存储起来，而不用返回给客户端，这里关于备份交换机就不做介绍。

包含全部参数的发送消息的方法为：basicPublish(String exchange, String routingKey, boolean mandatory, boolean immediate, BasicProperties props, byte[] body)，其中有两个参数我们之前还没有关注过：mandatory和immediate，下面我们简单介绍一下两者的作用及使用方式。

## mandatory参数和ReturnListener
发送消息将mandatory设为true时，如果消息无法路由到相应的队列，RabbitMQ会给生产者发送一条return消息；如果设置为false（不设置的话，默认为false），如果路由不到队列，RabbitMQ不发送任何消息，消息也就丢失了。一般mandatory都会结合ReturnListener使用，ReturnListener的handleReturn方法会返回发送的消息内容、路由键、交换机、属性，还包含响应码和响应内容。

## immediate参数

当immediate参数设为true 时，如果交换器在将消息路由到队列时发现队列上并不存在任何消费者，那么这条消息将不会存入队列中。当与路由键匹配的所有队列都没有消费者时，该消息会通过Basic.Return 返回至生产者。immediate参数告诉服务器， 如果该消息关联的队列上有消费者， 则立刻投递；如果所有匹配的队列上都没有消费者，则直接将消息返还给生产者， 不用将消息存入队列而等待消费者了。RabbitMQ 3 .0 版本开始去掉了对该参数的支持（官方解释是immediate标记会影响镜像队列性能，增加代码复杂性，并建议采用"设置消息TTL"和"DLX"等方式替代，下一节将会介绍），如果将immediate设为true，则发送消息时会发生如下错误：
```
2019-03-31 18:02:59,078 [AMQP Connection 192.168.74.4:5672] [com.rabbitmq.client.impl.ForgivingExceptionHandler]-[WARN] An unexpected connection driver error occured (Exception message: Connection reset)
```
查看rabbit@hostname.log，可以看到如下错误日志：
```
=ERROR REPORT==== 31-Mar-2019::18:03:35 ===
Error on AMQP connection <0.6864.0> (192.168.74.1:55026 -> 192.168.74.4:5672, vhost: '/myvhost', user: 'wkp', state: running), channel 1:
operation basic.publish caused a connection exception not_implemented: "immediate=true"
```

## 代码说明
我们故意在发送消息时设置错误的routing key，通过channel.addReturnListener(ReturnListener listener)设置了监听，然后运行观察控制台输出结果（注意不要关闭连接）




