package com.lwq.demo10_mandatory_returnlistener_immediate;

import java.io.IOException;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.*;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Sender {

	private final static String EXCHANGE_NAME = "mandatory_exchange";
	private final static String QUEUE_NAME = "mandatory_queue";

	public static void main(String[] argv) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		// 从连接中创建通道
		Channel channel = connection.createChannel();

		// 声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
		//声明队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//绑定
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");

		channel.addReturnListener(new ReturnListener() {

			@Override
			public void handleReturn(int replyCode,
			                         String replyText,
			                         String exchange,
			                         String routingKey,
			                         AMQP.BasicProperties properties,
			                         byte[] body)
					throws IOException {
				System.out.println("消息没有路由到队列==============");
				System.out.println("replyCode:"+replyCode);
				System.out.println("replyText:"+replyText);
				System.out.println("exchange:"+exchange);
				System.out.println("routingKey:"+routingKey);
				System.out.println("properties:"+properties);
				System.out.println("message:"+new String(body));
			}
		});

		String message = "这是一条测试mandatory的日志";
		channel.basicPublish(EXCHANGE_NAME, "info", true, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
		/*channel.basicPublish(EXCHANGE_NAME, "info", true, true, MessageProperties.PERSISTENT_TEXT_PLAIN,
				message.getBytes());*/
		System.out.println("Sent message:" + message);
		// 关闭通道和连接
		//		channel.close();
		//		connection.close();

		/*
		* 运行结果：消息因为没有符合条件的队列，被RabbitMQ发送return命令返回给了消息生产者。返回的replyCode=312，replyText=NO_ROUTE，表示没有可以路由的队列。
		* */
	}
}
