package com.lwq.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

/**
 * @author lwq
 * @date 2021/5/28 0028
 */
@RestController
public class TestController {

	@Autowired
	private RestTemplate restTemplate;

	// @Autowired
	// private CircuitBreakerFactory circuitBreakerFactory;

	@GetMapping("/hello")
	@SentinelResource("resource")
	public String hello() {
		return "Hello";
	}

	@GetMapping("/aa")
	@SentinelResource("aa")
	public String aa(int b, int a) {
		return "Hello test";
	}

	@GetMapping("/test")
	public String test1() {
		return "Hello test";
	}

	@GetMapping("/template")
	public String client() {
		return restTemplate.getForObject("http://www.taobao.com/test", String.class);
	}
}
