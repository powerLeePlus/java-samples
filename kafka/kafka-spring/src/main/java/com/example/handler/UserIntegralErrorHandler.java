package com.example.handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * kafka cumsum error handler
 * @author lwq
 * @date 2020/4/24 0024
 */
@Component("userIntegralErrorHandler")
public class UserIntegralErrorHandler {
	@Autowired
	private KafkaTemplate kafkaTemplate;

	// lister消费失败，在这里进行修复
	public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
		System.out.println("出错了，2222" + consumer.toString());
		MessageHeaders headers = message.getHeaders();
		String offset = headers.get(KafkaHeaders.OFFSET).toString();
		System.out.println("出场的offset: " + offset);
		return null;
	}


	public static void main(String[] args) {
		Long aLong = JSON.parseObject("sss", Long.class);
	}

}
