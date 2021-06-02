package com.lwq.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lwq
 * @date 2021/6/2 0002
 */
@Configuration
public class EchoFallbackHandlerFactory {

	@Bean
	public EchoFallbackHandler echoFallbackHandler() {
		return new EchoFallbackHandler();
	}
}
