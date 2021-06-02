package com.lwq.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lwq
 * @date 2021/5/28 0028
 */
@SpringBootApplication
public class SentinelSCGApplication {
	public static void main(String[] args) {
		// GatewayCallbackManager.setRequestOriginParser(s -> "123");
		SpringApplication.run(SentinelSCGApplication.class, args);
	}

}
