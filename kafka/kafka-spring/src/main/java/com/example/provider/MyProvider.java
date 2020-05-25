package com.example.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author lwq
 * @date 2020/4/24 0024
 */
@Component
public class MyProvider {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public String send(String topic, String msg) {
		kafkaTemplate.send(topic, msg);
		return msg;
	}
}
