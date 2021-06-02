package com.lwq.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author lwq
 * @date 2021/6/2 0002
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SentinelFeignProviderApplication {
	public static void main(String[] args) {
		SpringApplication.run(SentinelFeignProviderApplication.class, args);
	}
}
