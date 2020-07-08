package com.lwq.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * 消息路由失败处理
 * @author lwq
 * @date 2020/7/8 0008
 */
@Service
public class ReturnCallBackHandlerB implements RabbitTemplate.ReturnCallback {
	Logger logger = LoggerFactory.getLogger(ReturnCallBackHandlerB.class);

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		String msg = new String(message.getBody());
		logger.warn("B-RETURN机制:消息路由失败,msg:{},replyCode:{},replyText:{},exchange:{},routingKey:{}",msg,replyCode,
				replyText,exchange,routingKey);
	}
}
