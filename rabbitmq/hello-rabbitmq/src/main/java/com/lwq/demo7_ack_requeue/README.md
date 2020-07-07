## 消息确认机制与重回队列

### RabbitMQ的消息状态
在我们上面验证消息持久化的过程中，在Messages中有两个状态：Ready 和 Unacked，但是要消费时设置为手动确认，下面分别对这两个状态做个简介。

- Ready：按字面意思就是准备好了，可以投递给消费者了，对于未开启持久化的消息写入内存即为Ready状态；如果开启持久化了，则要持久化到磁盘之后才会变成Ready状态。

- Unacked（Unacknowledged——未确认的）：表示已经投递到消费者但是还没有收到消费者Ack确认时的消息状态。

验证方式：我们可以把持久化的代码示例稍作修改，在消费者调用channel.basicAck方法之前休眠个几十秒即可，然后查询消息的状态如下所示：可以看到Unacked状态的消息条数为1。

### 消费者Ack机制与重回队列
为了保证消息从队列可靠地达到消费者， RabbitMQ 提供了消息确认机制( message acknowledgement，简称Ack) 。
我们上面的例子中其实已经用到了这点，通过channel.basicConsume(String queue, boolean autoAck, Consumer callback) 订阅消费队列上的消息时，第二个参数autoAck表示是否自动确认。
当autoAck 设为true 时， RabbitMQ 会自动把发送出去的消息置为确认，然后从内存（或者磁盘）中删除；
当autoAck 设为false时， RabbitMQ 会等待消费者通过 basicAck(long deliveryTag, boolean multiple)方法，显式地回复确认信号后才从内存（或者磁盘）中移去消息（实质上是先打上删除标记，之后再删除）。

当autoAck 参数置为false ，对于RabbitMQ 服务端而言，队列中的消息分成了两个部分：一部分是等待投递给消费者的消息，即上面介绍的Ready状态；一部分是己经投递给消费者，但是还没有收到消费者确认信号的消息，即Unacked状态。
如果RabbitMQ 一直没有收到消费者的确认信号，并且消费此消息的消费者己经断开连接，则RabbitMQ 会安排该消息重新进入队列，等待投递给下一个消费者。可以通过消费者休眠，把消费者关掉然后再启动即可验证。

消费者确认：basicAck(long deliveryTag, boolean multiple)，其中deliveryTag 可以看作消息的编号，它是一个64 位的长整型值；multiple一般设为false，如果设为true则表示确认当前deliveryTag 编号及之前所有未被当前消费者确认的消息。

消费者拒绝：basicNack(long deliveryTag, boolean multiple, boolean requeue)，其中deliveryTag 可以看作消息的编号，它是一个64 位的长整型值。multiple一般设为false，如果设为true则表示拒绝当前deliveryTag 编号及之前所有未被当前消费者确认的消息。requeue参数表示是否重回队列，如果requeue 参数设置为true ，则RabbitMQ 会重新将这条消息存入队列尾部（注意是队列尾部），等待继续投递给订阅该队列的消费者，当然也可能是自己；如果requeue 参数设置为false ，则RabbitMQ立即会把消息从队列中移除，而不会把它发送给新的消费者。


### 代码说明
我们发送消息的时候，传递一个消息的序号，在消费者处理的时候判断这个序号如果为1则拒绝，其他情况都确认接收