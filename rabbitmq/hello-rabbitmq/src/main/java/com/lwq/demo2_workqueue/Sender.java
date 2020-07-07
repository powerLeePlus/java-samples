package com.lwq.demo2_workqueue;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Sender {

	private final static String QUEUE_NAME = "work_queue";

	public static void main(String[] argv) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		// 从连接中创建通道
		Channel channel = connection.createChannel();

		// 声明（创建）队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		// 消息内容
		for(int i=0;i<10;i++) {
			String message = "工作"+i;
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
		//关闭通道和连接
		channel.close();
		connection.close();
	}

}
