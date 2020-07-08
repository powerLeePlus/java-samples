package com.lwq;

import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lwq.common.Order;

/**
 * @author lwq
 * @date 2020/7/8 0008
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerApplicationTests {
	@Resource
	private RabbitSender rabbitSender;
	@Resource
	private RabbitTemplate singleRabbitTemplate;

	@Test
	public void contextLoads() {
	}

	/**
	 * 测试多例RabbitTemplate发送消息
	 * @throws Exception
	 */
	@Test
	public void test_scopeSendMsg() throws Exception {
		rabbitSender.scopeSend("多例测试111", null);
		rabbitSender.scopeSend("多例测试222", null);
		Thread.sleep(10000L);
	}

	/**
	 * 测试单例RabbitTemplate发送消息
	 * @throws Exception
	 */
	@Test
	public void test_singleSendMsg() throws Exception {
		Order order = new Order(1L, "123456", "测试订单");
		rabbitSender.singleSend(order, new HashMap<>());
		Thread.sleep(10000L);
	}

	/**
	 * 测试单例RabbitTemplate发送消息无法被路由
	 * @throws Exception
	 */
	@Test
	public void test_singleSendMsg_NoRoute() throws Exception {
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().replace("-", ""));
		singleRabbitTemplate.convertAndSend("springboot_direct_exchange", "error_key", "无法路由的消息",correlationData);
	}
}
