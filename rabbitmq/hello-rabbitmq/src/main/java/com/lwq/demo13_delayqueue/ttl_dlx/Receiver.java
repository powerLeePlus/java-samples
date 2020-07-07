package com.lwq.demo13_delayqueue.ttl_dlx;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.*;

/**
 * @author lwq
 * @date 2020/7/7 0007
 */
public class Receiver {

	public static void main(String[] argv) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		final Channel channel = connection.createChannel();

		channel.exchangeDeclare("delay_exchange_5s", "direct", true, false, null);
		channel.queueDeclare("delay_queue_5s", true, false, false, null);
		channel.queueBind("delay_queue_5s", "delay_exchange_5s", "q5s");

		// 指该消费者在接收到队列里的消息但没有返回确认结果之前,它不会将新的消息分发给它。
		channel.basicQos(1);

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
			                           byte[] body)
					throws IOException {
				System.out.println("消费者收到消息:" + new String(body)+",当前时间:"+ LocalDateTime.now(ZoneId.systemDefault()).toString());
				// 消费者手动发送ack应答
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		System.out.println("消费延迟5秒队列中的消息======================");
		// 监听队列
		channel.basicConsume("delay_queue_5s", false, consumer);
	}

}
