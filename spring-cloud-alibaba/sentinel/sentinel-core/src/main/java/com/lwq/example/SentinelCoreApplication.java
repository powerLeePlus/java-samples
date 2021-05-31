package com.lwq.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;

/**
 * @author lwq
 * @date 2021/5/28 0028
 */
@SpringBootApplication
public class SentinelCoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(SentinelCoreApplication.class, args);
	}

	@Bean
	@SentinelRestTemplate
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public RestTemplate restTemplate2() {
		return new RestTemplate();
	}

	// @Bean
	// public Converter myConverter() {
	// 	return new JsonFlowRuleListConverter();
	// }

	// @Bean
	// public Customizer<SentinelCircuitBreakerFactory> defaultConfig() {
	// 	return factory -> {
	// 		factory.configureDefault(
	// 				id -> new SentinelConfigBuilder().resourceName(id)
	// 						.rules(Collections.singletonList(new DegradeRule(id)
	// 								.setGrade(RuleConstant.DEGRADE_GRADE_RT).setCount(100)
	// 								.setTimeWindow(10)))
	// 						.build());
	// 	};
	// }
}
