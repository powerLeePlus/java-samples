# 高级特性之TTL（Time-To-Live消息有效期）

## TTL概述
RabbitMQ的TTL全称为Time-To-Live，表示的是消息的有效期。消息如果在队列中一直没有被消费并且存在时间超过了TTL，消息就会变成了"死信" (Dead Message)，后续无法再被消费了。设置TTL有两种方式：

- 第一种是声明队列的时候，在队列的属性中设置，这样该队列中的消息都会有相同的有效期；
- 第二种是发送消息时给消息设置属性，可以为每条消息都设置不同的TTL。
如果两种方式都设置了，则以设置的较小的为准。两者的区别：如果声明队列时设置了有效期，则消息过期了就会被删掉；如果是发消息时设置的有效期，消息过期了也不会被立马删掉，因为这时消息是否过期是在要投递给消费者时判断的。至于为啥要这样处理很容易想清楚：第一种方式队列的消息有效期都一样，先入队的在队列头部，头部也是最早要过期的消息，RabbitMQ起一个定时任务从队列的头部开始扫描是否有过期消息即可；第二种方式每条消息的过期时间不同，所以只有遍历整个队列才可以筛选出来过期的消息，这样效率太低了，而且消息量大了之后根本不可行的，可以等到消息要投递给消费者时再判断删除，虽然删除的不够及时但是不影响功能，其实就是用空间换时间。

如果不设置TTL，则表示此消息永久有效（默认消息是不会失效的）。如果将TTL设为0，则表示如果消息不能被立马消费则会被立即丢掉，这个特性可以部分替代RabbitMQ3.0以前支持的immediate参数，之所以所部分代替，是应为immediate参数在投递失败会有basic.return方法将消息体返回（这个功能可以利用死信队列来实现）。

## 设置TTL

### 1、通过队列设置有效期
还记得我们之前声明队列的方法吗，queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)，
该方法的最后一个参数可以设置队列的属性，属性名为`x-message-ttl`，单位为毫秒。如果不清楚队列属性有哪些，可以查看web控制台的添加队列的地方。
```
//设置队列上所有的消息的有效期,单位为毫秒
Map<String, Object> argss = new HashMap<String , Object>();
arguments.put("x-message-ttl " , 5000);//5秒钟
channel.queueDeclare(queueName , durable , exclusive , autoDelete , arguments) ;
```

查看控制台的队列列表如下：D表示持久化，TTL表示设置了消息的有效期。

也可以用RabbitMQ的命令行模式来设置：
```
rabbitmqctl set_policy TTL ".*" '{"message-ttl":60000}' --apply-to queues
```

还可以通过HTTP接口调用：
```
$ curl -i -u guest:guest -H "content-type:application/json"  -XPUT -d'{"auto_delete":false,"durable":true,"arguments":{"x-message-ttl": 60000}}' 
http://ip:15672/api/queues/{vhost}/{queuename}
```
### 2、通过发送消息时设置有效期
发送消息时basicPublish方法可以设置属性参数，里面通过expiration属性设置消息有效期，单位为毫秒，代码如下所示
```
Builder bd = new AMQP.BasicProperties().builder();
bd.deliveryMode(2);//持久化
bd.expiration("100000");//设置消息有效期100秒钟
BasicProperties pros = bd.build();
String message = "测试ttl消息";
channel.basicPublish(EXCHANGE_NAME, "error", true,false, pros, message.getBytes());
```
另外也可以通过HTTPAPI 接口设置：
```
$ curl -i -u guest:guest -H "content-type:application/json"  -XPOST -d
'{"properties":{"expiration":"60000"},"routing_key":"routingkey","payload":"my body","payload_encoding":"string"}'  
http://localhost:15672/api/exchanges/{vhost}/{exchangename}/publish
```

### 3、设置队列有效期（expire)(不常用）
有一个x-expires参数，可以让队列在指定时间内 "未被使用" 的话会自动过期删除，
未使用的意思是 queue 上没有任何 consumer，queue 没有被重新声明，并且在过期时间段内未调用过 basic.get 命令。
该方式可用于，例如，RPC-style 的回复 queue, 其中许多queue 会被创建出来，但是却从未被使用。
```
Map<String, Object> args = new HashMap<String, Object>();  
args.put("x-expires", 18000);  //队列有效期18秒
channel.queueDeclare("myqueue", false, false, false, args);  
```

### 代码说明
完整的通过队列设置消息有效期、发布消息时通过属性设置有效期的代码结果：可以运行后，观察下控制台，可以发现同时设置时，消息的有效期是以较小的为准的。