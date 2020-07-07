# direct exchange
本例我们将通过一个发送日志的程序来演示direct类型的exchange的routing功能。
生产者发送日志，级别分别为info，warn，error等，消费者只会接收routing key 跟其队列的binding key完全匹配的消息。

### 1.Bindings（绑定）
绑定是交换机和队列之间的关系。这可以简单地理解为：队列对来自此交换机的消息感兴趣。绑定可以采用额外的routingKey参数。为了避免与basic_publish参数混淆，我们将其称为 binding key
。这就是我们如何使用键创建绑定：
```
channel.queueBind（queueName，EXCHANGE_NAME，“black”）;
```
绑定键的意义依赖于转发器的类型。对于fanout类型，忽略此参数。

### 2.Direct exchange（直接转发）
使用fanout类型的交换机，只能够对消息进行转发，不够灵活，不能够对消息按照一定的规则进行转发，而direct类型的交换机则可以做到这一点。
direct类型的交换机背后的路由转发算法很简单：消息会被推送至binding key和消息routing key完全匹配的队列。图解：
![direct exchange](direct%20exchange.png)

### 3.multiple bindings（多重绑定）
使用相同的binding key绑定多个队列是完全合法的。在我们的示例中，我们可以在X和Q1之间添加绑定键black绑定。
在这种情况下，direct交换机将表现得想fanout交换机一样，并将消息广播到所有匹配的队列。路由键为black的消息将传送到Q1和Q2。
![multiple bindings](multiple%20bindings.png)

## 程序说明
我们通过消费者发送info，warn，error的日志，发送的日志级别（也就是routing key）是随机的。消费者有两个，消费者1只接收info级别的日志，消费者2只接收warn，error的日志。
注意：这里消费者1和2是两个不同的队列，消费者2有两个binding key，可以通过多次调用channel.queueBind()来添加多个binding key，如下所示：
```
channel.queueBind（queueName，EXCHANGE_NAME，"warn"）;
channel.queueBind（queueName，EXCHANGE_NAME，"error"）;
```
   
