package com.lwq.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

/**
 * @author lwq
 * @date 2021/6/4 0004
 */
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = false)
@EnableFeignClients
public class BusinessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@FeignClient(value = "order-service")
	public interface OrderService {
		@PostMapping(value = "/order")
		public String order(@RequestParam("userId") String userId, @RequestParam("commodityCode") String commodityCode, @RequestParam("orderCount") int orderCount);
	}

	@FeignClient(value = "storage-service")
	public interface StorageService {
		@GetMapping(value = "/storage/{commodityCode}/{count}")
		public String echo(@PathVariable("commodityCode") String commodityCode, @PathVariable("count") int count);
	}
}
