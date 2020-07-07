package com.lwq.consumer;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

/**
 * @author lwq
 * @date 2020/7/7 0007
 */
public class ConsumerHandler implements ChannelAwareMessageListener {
	Logger logger = LoggerFactory.getLogger(ConsumerHandler.class);

	@Override
	public void onMessage(Message message, Channel channel) throws Exception {

		String msg = new String(message.getBody(),"utf-8");
		MessageProperties pros = message.getMessageProperties();
		Map<String, Object> headers = pros.getHeaders();
		String routingKey = pros.getReceivedRoutingKey();
		logger.info("消费者收到消息:{},routingKey:{},headers:{}",msg,routingKey,headers);
		//消费者ack确认 这里消费者是手动ack确认的
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}
}
