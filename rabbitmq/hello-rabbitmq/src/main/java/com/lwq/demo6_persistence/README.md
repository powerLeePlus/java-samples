## 持久化

为了防止RabbitMQ的消息丢失，我们可以开启持久化开关，
RabbitMQ的持久化分三个部分：交换机的持久化、队列的持久化、消息的持久化。
注意：开启消息持久化的话，因为要写入磁盘文件，效率肯定要比不开启要低一些，不过是可以接受的，生产上一般都会开启持久化的，除非消息不是特别重要，可以容忍消息的丢失。

- 交换机持久化：可以在声明交换机的时候将durable设为true，例如channel.exchangeDeclare( "persitent_exchange" ,  "fanout" ,  true , false , null
) 表示声明了一个名字为persitent_exchange、fanout类型、持久化、非自动删除的exchange。如果交换机不设置持久化，那么RabbitMQ重启之后该交换机就不存在了，不过消息还在，但是不能往该交换机发送消息，一般都会设为持久化的。

- 队列持久化：可以在声明队列的时候将durable设为true，例如 channel.queueDeclare("persistent_queue", true, false, false, null
) 表示声明了一个名字为persistent_queue、持久化、非排他、非自动删除的队列。如果队列不设置持久化，那么RabbitMQ重启之后该队列上的消息就丢失了。

- 消息持久化：发送消息的方法为channel.basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body
)，持久化可以在第三个参数props中设置，其部分源码如下：里面列举了所有的14种属性
```java
public static class BasicProperties extends com.rabbitmq.client.impl.AMQBasicProperties {
        private String contentType;
        private String contentEncoding;
        private Map<String,Object> headers;
        private Integer deliveryMode;
        private Integer priority;
        private String correlationId;
        private String replyTo;
        private String expiration;
        private String messageId;
        private Date timestamp;
        private String type;
        private String userId;
        private String appId;
        private String clusterId;
 
        //省略............
}
```
其中deliveryMode为持久化相关的属性，将deliveryMode设为2表示设置消息持久化，可以通过如下代码设置持久化属性

BasicProperties props=new BasicProperties().builder().deliveryMode(2).build();

还可以通过BasicProperties props= MessageProperties.PERSISTENT_TEXT_PLAIN; 的方式设置，其实这种方式只不过是上面的封装罢了。
