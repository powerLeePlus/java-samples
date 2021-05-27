package com.lwq.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;

/**
 * @author lwq
 * @date 2021/5/26 0026
 */
@SpringBootApplication
@EnableDiscoveryClient(autoRegister = true)
@EnableFeignClients
public class ConsumerApplication {

	/**
	 * 定义RestTemplate并启用负载均衡
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@LoadBalanced
	@SentinelRestTemplate
	public RestTemplate restTemplate1() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	/**
	 * 定义FeignClient
	 */
	// @FeignClient(name = "service-provider")
	@FeignClient(name = "service-provider", fallback = EchoServiceFallback.class, configuration = FeignConfiguration.class)
	public interface EchoService {
		/**
		 * 定义与服务提供者一致的请求定义
		 */
		@GetMapping("/echo/{str}")
		String echo(@PathVariable("str") String str);

		@GetMapping("/divide")
		String divide(@RequestParam("a") Integer a, @RequestParam("b") Integer b);

		default String divide(Integer a) {
			return divide(a, 0);
		}

		@GetMapping("/notFound")
		String notFound();
	}

	class FeignConfiguration {

		/**
		 * Feign容错的配置-容错接口实例需由spring容器管理
		 */
		@Bean
		public EchoServiceFallback echoServiceFallback() {
			return new EchoServiceFallback();
		}

	}

	/**
	 * Feign容错的配置-定义容错接口（必须实现@FeignClient注解的接口）
	 * 需要与限流熔断器配合使用（如Sentinel）
	 */
	class EchoServiceFallback implements EchoService {

		@Override
		public String echo(@PathVariable("str") String str) {
			return "echo fallback";
		}

		@Override
		public String divide(@RequestParam Integer a, @RequestParam Integer b) {
			return "divide fallback";
		}

		@Override
		public String notFound() {
			return "notFound fallback";
		}

	}
}
