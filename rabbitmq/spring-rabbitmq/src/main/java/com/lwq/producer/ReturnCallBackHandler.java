package com.lwq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author lwq
 * @date 2020/7/7 0007
 */
public class ReturnCallBackHandler implements RabbitTemplate.ReturnCallback {
	Logger logger = LoggerFactory.getLogger(ConfirmCallBackHandler.class);

	/**
	 * return回调处理类：当消息无法被路由时会触发return回调处理，并将发送的消息返回
	 */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		String msg = new String(message.getBody());
		logger.warn("RETURN机制:消息路由失败,msg:{},replyCode:{},replyText:{},exchange:{},routingKey:{}",msg,replyCode,replyText,exchange,routingKey);
	}
}
