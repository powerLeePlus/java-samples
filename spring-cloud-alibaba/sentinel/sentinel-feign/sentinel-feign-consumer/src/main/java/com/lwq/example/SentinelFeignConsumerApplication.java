package com.lwq.example;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lwq
 * @date 2021/6/2 0002
 */
@SpringCloudApplication
// @EnableDiscoveryClient
@EnableFeignClients
public class SentinelFeignConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SentinelFeignConsumerApplication.class, args);
	}
}
