package com.lwq.example;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import reactor.core.publisher.Mono;

/**
 * @author lwq
 * @date 2021/6/1 0001
 */
@Configuration
public class MySCGConfiguration {

	/**
	 * 自定义Spring cloud gateway Sentinel Block 异常处理
	 */
	@Bean
	public BlockRequestHandler blockRequestHandler() {
		return new BlockRequestHandler() {
			@Override
			public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
				return ServerResponse.status(444).contentType(MediaType.APPLICATION_JSON)
						.body(fromValue("SCS Sentinel block"));
			}
		};
	}
}
