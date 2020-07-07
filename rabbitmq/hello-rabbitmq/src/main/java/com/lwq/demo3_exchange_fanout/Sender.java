package com.lwq.demo3_exchange_fanout;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Sender {
	private final static String EXCHANGE_NAME = "fanout_exchange";

	public static void main(String[] argv) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		// 从连接中创建通道
		Channel channel = connection.createChannel();

		// 声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
		// 由于使用fanout类型的交换机时，routing key是不起作用的，所以代码中生产者发送消息、消费者中交换机与队列绑定时，routing key设置的都是空字符串，
		// 你可以将routing key改为任意值，你发现消费者都还是可以收到消息的。

		// 消息内容
		String message = "这是一条fanout类型交换机消息";
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println("Sent message:" + message);
		//关闭通道和连接
		channel.close();
		connection.close();
	}

}
