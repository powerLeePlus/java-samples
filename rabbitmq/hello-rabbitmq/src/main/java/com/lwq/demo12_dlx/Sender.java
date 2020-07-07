package com.lwq.demo12_dlx;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Sender {

	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		//声明一个交换机，做死信交换机用
		channel.exchangeDeclare("dlx_exchange", "topic", true, false, null);
		//声明一个队列，做死信队列用
		channel.queueDeclare("dlx_queue", true, false, false, null);
		//队列绑定到交换机上
		channel.queueBind("dlx_queue", "dlx_exchange", "dlx.*");

		channel.exchangeDeclare("normal_exchange", "fanout", true, false, null);
		Map<String, Object> arguments=new HashMap<String, Object>();
		arguments.put("x-message-ttl" , 5000);//设置消息有效期5秒，过期后变成死信消息，然后进入DLX
		arguments.put("x-dead-letter-exchange" , "dlx_exchange");//设置DLX
		arguments.put("x-dead-letter-routing-key" , "dlx.test");//设置DLX的路由键(可以不设置)
		//为队列normal_queue 添加DLX
		channel.queueDeclare("normal_queue", true, false, false, arguments);
		channel.queueBind("normal_queue", "normal_exchange", "");

		channel.basicPublish("normal_exchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, ("测试死信消息").getBytes());
		System.out.println("发送消息时间:" + LocalDateTime.now().toString());

		channel.close();
		connection.close();

		/*
		* 运行结果如下（先运行的死信队列消费者，然后运行生产者）：我们看到消息过期后28毫秒就被死信队列的消费者消费到了，显然，消息成为死信后是立即被发送到了DLX中。
		*
		*** 发送消息时间:2020-07-07T14:56:41.622
		*** 收到死信消息：测试死信消息 ；时间：2020-07-07T14:56:46.594
		*/
	}

}
