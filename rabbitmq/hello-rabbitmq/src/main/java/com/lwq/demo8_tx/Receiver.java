package com.lwq.demo8_tx;

import java.io.IOException;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.*;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Receiver {

	private static final String EXCHANGE_NAME = "tx_exchange1";
	private static final String QUEUE_NAME = "tx_queue1";

	public static void main(String[] args) throws Exception {
		// 获取连接和mq通道
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		// 申明exchange
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		// 申明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		// 绑定队列到交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

		// 指定消费者在收到消息后但是没有返回确认结果之前，broker不会再给它发消息
		channel.basicQos(1);

		// 定义消费者
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				System.out.println("收到消息：" + new String(body));
				//消费者手动发送ack应答
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};

		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}
