package com.lwq.demo7_ack_requeue;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Sender {
	private final static String EXCHANGE_NAME = "ack_exchange";

	public static void main(String[] argv) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		// 从连接中创建通道
		Channel channel = connection.createChannel();

		// 声明交换机-持久化，非自动删除
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true,false,null);

		// 消息内容
		for(int i=0;i<4;i++) {
			String message = "消息:"+i;
			AMQP.BasicProperties props= MessageProperties.PERSISTENT_TEXT_PLAIN;
			channel.basicPublish(EXCHANGE_NAME, "", props, message.getBytes());
			System.out.println("Sent message:" + message);
		}
		//关闭通道和连接
		channel.close();
		connection.close();
	}

}
