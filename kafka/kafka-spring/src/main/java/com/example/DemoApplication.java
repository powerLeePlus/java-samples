package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * @author lwq
 * @date 2020/4/24 0024
 */
@SpringBootApplication
@EnableKafka
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class);
	}
}
