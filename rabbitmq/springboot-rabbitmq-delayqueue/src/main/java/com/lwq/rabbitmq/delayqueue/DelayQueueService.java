package com.lwq.rabbitmq.delayqueue;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author lwq
 * @date 2020/7/8 0008
 */
@Service
public class DelayQueueService {
	Logger logger = LoggerFactory.getLogger(DelayQueueService.class);

	@Resource
	private RabbitTemplate singleRabbitTemplate;
	@Resource
	private ApplicationContext applicationContext;

	/**
	 * 采用单例的RabbitTemplate发送消息方法调用: 构建Message消息<br/>
	 * @param value
	 * @throws Exception
	 */
	public void sendDelayMessage(String value) {
		//id + 时间戳 全局唯一
		CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().replace("-", ""));
		singleRabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE, RabbitConfig.DELAY_ROUTING_KEY, value,
				message -> {
					// 设置延迟（毫秒）：1分钟
					message.getMessageProperties().setExpiration(String.valueOf(60000));
					return message;
				},
				correlationData);
		logger.info("发送到队列消息：{} ，当前时间：{}", value, LocalDateTime.now());
	}
}
