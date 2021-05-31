package com.lwq.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lwq
 * @date 2021/5/28 0028
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosGatewayProviderApplication {
	public static void main(String[] args) {
		SpringApplication.run(NacosGatewayProviderApplication.class, args);
	}

	@RestController
	class EchoController {

		@GetMapping("/echo/{string}")
		public String echo(@PathVariable String string) {
			return "hello Nacos Discovery " + string;
		}

		@GetMapping("/divide")
		public String divide(@RequestParam Integer a, @RequestParam Integer b) {
			return String.valueOf(a / b);
		}

	}
}
