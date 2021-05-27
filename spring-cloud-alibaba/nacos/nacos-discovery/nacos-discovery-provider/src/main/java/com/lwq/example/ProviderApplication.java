package com.lwq.example;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lwq
 * @date 2021/5/15 0015
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class, args);
	}

	@RestController
	public class EchoController {
		@GetMapping("/echo/{string}")
		public String echo(@PathVariable String string) {
			System.out.println("当前时间echo" + LocalDateTime.now().toString());
			return string;
		}

		@GetMapping("/")
		public ResponseEntity index() {
			return new ResponseEntity("index error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		@GetMapping("/test")
		public ResponseEntity test() {
			return new ResponseEntity("error", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		@GetMapping("/divide")
		public String divide(@RequestParam Integer a, @RequestParam Integer b) {
			return String.valueOf(a / b);
		}

		@GetMapping("/sleep")
		public String sleep() {
			try {
				Thread.sleep(1000L);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("当前时间sleep" + LocalDateTime.now().toString());
			return "ok";
		}
	}
}
