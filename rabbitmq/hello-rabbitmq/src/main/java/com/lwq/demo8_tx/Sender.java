package com.lwq.demo8_tx;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Sender {

	private static final String EXCHANGE_NAME = "tx_exchange1";

	public static void main(String[] args) throws Exception {
		// 获取连接和mq通道
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		// 申明exchange
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

		// 消息
		try {

			channel.txSelect(); // 开启事务

			String msg = "这是一条使用事务的消息";
			channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
			// channel.basicPublish(EXCHANGE_NAME, "sss", null, msg.getBytes());
			System.out.println("Sent message:" + msg);

			// 可以模拟异常，回滚事务
			// int i = 1 / 0;

			channel.txCommit(); // 提交事务
		} catch (Exception e) {
			e.printStackTrace();
			// 异常，回滚事务
			channel.txRollback();
		} finally {
			// 关闭释放资源
			channel.close();
			connection.close();
		}

	}
}
