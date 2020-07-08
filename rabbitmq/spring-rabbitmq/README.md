# RabbitMQ与Spring的整合使用：
spring-amqp和spring-rabbit，RabbitTemplate、MessageListener的使用介绍，如何发送消息，如何接收消息，如何设置消费者ack确认，如何添加confirm和return回调

项目详细介绍地址为https://spring.io/projects/spring-amqp，
我们看到红线圈住的部分：该项目包含两部分，spring-amqp是基础抽象，而spring-rabbit是spring-amqp的一个具体的实现。
Spring官方的提供的demo地址为https://github.com/spring-projects/spring-amqp-samples，
官方参考文档地址为https://docs.spring.io/spring-amqp/docs/1.7.5.RELEASE/reference/html/，
可以选择查看对应的版本的文档。

## 代码说明

### 1.Spring配置文件rabbitmq-context.xml内容：
spring-rabbit的常用配置都在里面，而且也都写了注释，就不再做过多的说明了。配置中包括重试策略，消息转换器，confirm回调，return回调等都是可选的，可以根据自己的需要进行配置。另外listener中还可以设置线程池等
- <rabbit:connection-factory>对应CachingConnectionFactory，可以用来创建连接
- <rabbit:admin>对应RabbitAdmin，可以声明交换机、队列、绑定
- <rabbit:topic-exchange>对应TopicExchange表示topic类型交换机（其他类型的交换机不再一一列出）
- <rabbit:queue>对应Queue，表示队列
- <rabbit:binding>对应Binding，用于交换机与队列的绑定
- <rabbit:template>对应RabbitTemplate，用于消息的发送等
- <rabbit:listener-container>对应MessageListenerContainer，表示消息监听容器
- <rabbit:listener>对应的是具体的消费者监听器，我是实现的ChannelAwareMessageListener

关于消息转换器我使用的是Jackson2JsonMessageConverter，其实一般发个简单文本或者json字符串消息就够日常使用了，如果想要发一些复杂的消息内容也可以自定义消息转换器，实现MessageConverter接口即可。

### 测试发布消息在`ProducerTest`中