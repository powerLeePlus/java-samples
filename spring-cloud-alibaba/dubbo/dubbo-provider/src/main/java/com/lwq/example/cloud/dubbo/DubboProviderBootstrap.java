package com.lwq.example.cloud.dubbo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lwq
 * @date 2021/6/16 0016
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
public class DubboProviderBootstrap {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DubboProviderBootstrap.class).properties("spring.profiles.active=nacos")
				.web(WebApplicationType.NONE).run(args);
	}
}
