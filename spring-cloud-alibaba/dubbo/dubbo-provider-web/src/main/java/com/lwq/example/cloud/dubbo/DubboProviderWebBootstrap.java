package com.lwq.example.cloud.dubbo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lwq
 * @date 2021/6/17 0017
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
public class DubboProviderWebBootstrap {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DubboProviderWebBootstrap.class)
				.properties("spring.profiles.active=nacos")
				.run(args);
	}
}
