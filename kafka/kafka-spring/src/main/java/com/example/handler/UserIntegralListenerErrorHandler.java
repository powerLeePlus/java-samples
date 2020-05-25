package com.example.handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

/**
 * kafka cumsum error handler
 * @author lwq
 * @date 2020/4/24 0024
 */
@Component("userIntegralListenerErrorHandler")
public class UserIntegralListenerErrorHandler implements KafkaListenerErrorHandler {

	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
		System.out.println("出错了，");
		return null;
	}

	// lister消费到数据，还没有交给线程执行时异常会走到这里
	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
		System.out.println("出错了，2222" + consumer.toString());
		MessageHeaders headers = message.getHeaders();
		String offset = headers.get(KafkaHeaders.OFFSET).toString();
		System.out.println("出场的offset: " + offset);
		return null;
	}

}
