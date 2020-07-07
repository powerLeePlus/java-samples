# DLX是Dead-Letter-Exchange的简写，意思是死信交换机。

## 1、什么是DLX
它的作用其实是用来接收死信消息（dead message）的。那什么是死信消息呢？一般消息变成死信消息有如下几种情况：
- 消息被拒绝(Basic.Reject/Basic.Nack) ，井且设置requeue 参数为false
- 消息过期
- 队列达到最大长度

当消息在一个队列中变成了死信消息后，可以被发送到另一个交换机，这个交换机就是DLX，绑定DLX的队列成为死信队列。当这个队列中存在死信时， RabbitMQ 就会立即自动地将这个消息重新发布到设置的DLX 上去，进而被路由到绑定该DLX的死信队列上。可以监听这个队列中的消息、以进行相应的处理，这个特性与将消息的TTL 设置为0 配合使用可以弥补imrnediate 参数的功能。

## 2、DLX的作用
因为消息如果未被正常消费并设置了requeue为false时会进入死信队列，我们可以监控消费死信队列中消息，来观察和分析系统的问题。DLX还有一个非常重要的作用，就是结合TTL实现延迟队列（延迟队列的使用范围还是挺广的：比如下单超过多长时间自动关闭；比如我们接入过第三方支付系统的同学一定知道，我们的订单中会传一个notify_url用于接收支付结果知，如果我们给第三方支付响应的不是成功的消息，其会隔一段时间继续调用通知我们的notify_url，超过几次后不再进行通知，一般通知频率都是 0秒-5秒-30秒-5分钟-30分钟-1小时-6小时-12小时；比如我们的家用电器定时关机。。。。。。这些场景都是可以用延迟队列实现的）。

## 3、DLX使用方式
在web管控台添加队列的时候，我们看到有两个DLX相关的参数：x-dead-letter-exchange和x-dead-letter-routing-key。x-dead-letter-exchange是设置队列的DLX的；x-dead-letter-routing-key是设置死信消息进入DLX时的routing key的，这个是可以不设置的，如果不设置，则默认使用原队列的routing key。
客户端可以通过channel.queueDeclare方法声明队列时设置x-dead-letter-exchange参数，具体代码如下所示
```
channel.exchangeDeclare("dlx_exchange" , "direct"); //创建DLX: dlx_exchange
Map<String, Object> args = new HashMap<String, Object>();
args.put("x-dead-letter-exchange" , "dlx_exchange ");//设置DLX
args.put("x-dead-letter-routing-key" , "dlx-routing-key");//设置DLX的路由键(可以不设置)
//为队列myqueue 添加DLX
channel.queueDeclare("myqueue" , false , false , false , args);
```

## 代码说明
- 先定义死信发送者，并启动：
queues视图如下：DLX和DLK表示设置给normal_queue设置了死信交换机和死信消息的routing key，我们看到消息已经被路由到了死信队列上面。整个流程为：

消息发送到交换机normal_exchange，然后路由到队列normal_queue上
因为队列normal_queue没有消费者，消息过期后成为死信消息
死信消息携带设置的x-dead-letter-routing-key=dlx.test进入到死信交换机dlx_exechage
dlx_exechage与dlx_queue绑定的routing key为"dlx.*"，死信消息的路由键dlx.test符合该规则被路由到dlx.queue上面。

- 然后我们给死信队列添加消费者

我们测试一下死信消息进入DLX的时间，（要先将之前的那个死信消息删除）
