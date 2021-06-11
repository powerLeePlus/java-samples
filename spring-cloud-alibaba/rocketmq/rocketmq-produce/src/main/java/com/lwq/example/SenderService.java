package com.lwq.example;

import static com.lwq.example.RocketmqProducerApplication.MySource;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * @author lwq
 * @date 2021/6/11 0011
 */
@Service
public class SenderService {
	@Autowired
	private MySource source;

	/**
	 * 发送消息方式一
	 */
	public void send(String msg) throws Exception {
		source.output1().send(MessageBuilder.withPayload(msg).build());
	}

	/**
	 * 发送消息方式二
	 */
	public <T> void sendWithTags(T msg, String tag) throws Exception {
		Message<T> message = MessageBuilder.createMessage(msg, new MessageHeaders(Stream.of(tag).collect(Collectors.toMap(str -> MessageConst.PROPERTY_TAGS, String::toString))));
		source.output1().send(message);
	}

	/**
	 * 发送消息方式三
	 */
	public <T> void sendObject(T msg, String tag) throws Exception {
		Message<T> message = MessageBuilder.withPayload(msg)
				.setHeader(MessageConst.PROPERTY_TAGS, tag)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.build();
		source.output1().send(message);
	}

	/**
	 * 发送消息方式四：支持事务
	 */
	public <T> void sendTransactionalMsg(T msg, int num) throws Exception {
		Message<T> message = MessageBuilder.withPayload(msg)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
				.setHeader("test", String.valueOf(num))
				.setHeader(RocketMQHeaders.TAGS, "binder")
				.build();
		source.output2().send(message);
	}
}
