package com.lwq.demo1_helloworld;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author lwq
 * @date 2020/7/3 0003
 */
public class OldReceiver {
	private final static String QUEUE_NAME = "test_queue";

	public static void main(String[] argv) throws Exception {

		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		// 定义队列的消费者
		QueueingConsumer consumer = new QueueingConsumer(channel);
		// 监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
		//while循环不让程序退出
		while(true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			System.out.println("老的消费者api收到消息:"+new String(delivery.getBody()));
		}
	}
}
