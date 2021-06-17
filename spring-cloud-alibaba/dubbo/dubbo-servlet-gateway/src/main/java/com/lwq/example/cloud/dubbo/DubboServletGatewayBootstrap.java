package com.lwq.example.cloud.dubbo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lwq
 * @date 2021/6/17 0017
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableFeignClients
@ServletComponentScan("com.lwq.example.cloud.dubbo")
public class DubboServletGatewayBootstrap {

	public static void main(String[] args) {
		new SpringApplicationBuilder(DubboServletGatewayBootstrap.class)
				.properties("spring.profiles.active=nacos")
				.run(args);
	}
}
