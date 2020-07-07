package com.lwq.demo13_delayqueue.plugin;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Sender {

	private final static String EXCHANGE_NAME = "delay_plugin_exchange";
	private final static String QUEUE_NAME = "delay_plugin_queue";
	private final static String ROUTING_KEY = "delay";

	public static void main(String[] argv) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		// 从连接中创建通道
		Channel channel = connection.createChannel();

		//声明x-delayed-type类型的exchange
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-delayed-type", "direct");
		channel.exchangeDeclare(EXCHANGE_NAME, "x-delayed-message", true, false, args);
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

		// 消息内容
		for (int i = 0; i < 5; i++) {
			Thread.sleep(1000);
			Map<String, Object> headers= new HashMap<String, Object>();
			long delayTime=2000;
			if(i%2==0) {
				delayTime=5000;
			}
			headers.put("x-delay",delayTime);//消息延迟时间
			AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
					.headers(headers).build();
			String message = "序号:" + i + ",时间: " + LocalDateTime.now(ZoneId.systemDefault()).toString();
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, props, message.getBytes());
			System.out.println("Sent message:" + message);
		}
		// 关闭通道和连接
		channel.close();
		connection.close();
	}

}