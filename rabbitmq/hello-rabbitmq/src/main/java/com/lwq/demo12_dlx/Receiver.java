package com.lwq.demo12_dlx;

import java.io.IOException;
import java.time.LocalDateTime;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.*;

/**
 * @author lwq
 * @date 2020/7/7 0007
 */
public class Receiver {

	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare("dlx_exchange", "topic", true, false, null);
		channel.queueDeclare("dlx_queue", true, false, false, null);
		channel.queueBind("dlx_queue", "dlx_exchange", "dlx.*");

		// 消费者在确认消息结果之前，broker不会再给它发消息
		channel.basicQos(1);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println("收到死信消息：" + new String(body) + " ；时间：" + LocalDateTime.now().toString());
				// 消费者手动发送ack应答
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};

		System.out.println("消费死信队列中的消息======================");
		// 监听队列
		channel.basicConsume("dlx_queue", false, consumer);

	}
}
