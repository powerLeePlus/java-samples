package com.example.controller;

import static com.example.config.KafkaConstants.DEFAULT_TOPIC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.provider.MyProvider;

/**
 * @author lwq
 * @date 2020/4/24 0024
 */
@RestController
public class providerController {
	@Autowired
	private MyProvider myProvider;

	@GetMapping("{msg}")
	public String send(@PathVariable String msg) {
		return myProvider.send(DEFAULT_TOPIC, msg);
	}

	@GetMapping("{topic}/{msg}")
	public String send(@PathVariable String topic, @PathVariable String msg) {
		return myProvider.send(topic, msg);
	}
}
