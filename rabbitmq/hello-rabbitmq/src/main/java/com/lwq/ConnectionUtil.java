package com.lwq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author lwq
 * @date 2020/7/3 0003
 */
public class ConnectionUtil {
	public static Connection getConnection() throws Exception {
		//定义连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		//设置服务地址
		factory.setHost("172.16.20.220");
		//端口
		factory.setPort(5672);
		//设置账号信息，用户名、密码、vhost
		// factory.setVirtualHost("/myvhost");  // 默认是/
		factory.setUsername("guest");
		factory.setPassword("guest");
		// 通过工程获取连接
		Connection connection = factory.newConnection();
		return connection;
	}

}
