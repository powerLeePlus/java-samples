package com.lwq.example.cloud.dubbo.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Dubbo Spring Cloud Server Bootstrap.
 * Dubbo Spring Cloud 引导类与普通 Spring Cloud 应用并无差别
 *
 * @author lwq
 * @date 2021/6/16 0016
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
public class DubboServerBootstrap {

	public static void main(String[] args) {
		SpringApplication.run(DubboServerBootstrap.class, args);
	}

}
