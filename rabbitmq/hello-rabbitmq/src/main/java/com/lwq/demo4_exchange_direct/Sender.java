package com.lwq.demo4_exchange_direct;

import java.util.Random;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Sender {

	private final static String EXCHANGE_NAME = "direct_exchange";

	private static String[] logLevels={"info","warn","error"};

	public static void main(String[] argv) throws Exception {
		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		// 从连接中创建通道
		Channel channel = connection.createChannel();

		// 声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		Random random = new Random();
		// 消息内容
		for(int i=0;i<10;i++){
			//随机得到路由键
			String routingKey = logLevels[random.nextInt(3)];
			String message = "日志序号i:"+i+",级别:"+routingKey;
			channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
			System.out.println("Sent message:" + message);
		}
		//关闭通道和连接
		channel.close();
		connection.close();
	}

}
