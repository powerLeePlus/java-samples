package com.lwq.demo1_helloworld;

import java.io.IOException;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.*;

/**
 * @author lwq
 * @date 2020/7/3 0003
 */
public class NewReceiver {
	private final static String QUEUE_NAME = "test_queue";

	public static void main(String[] argv) throws Exception {

		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		// 定义队列的消费者
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag,
			                           Envelope envelope,
			                           AMQP.BasicProperties properties,
			                           byte[] body) throws IOException {
				System.out.println("新的消费者api收到消息:" + new String(body));
			}
		};
		// 监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);

	}

}
