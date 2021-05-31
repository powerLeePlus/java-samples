package com.lwq.example;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

/**
 * @author lwq
 * @date 2021/5/28 0028
 */
@RestController
public class TestController {

	@Resource(name = "restTemplate")
	private RestTemplate restTemplate;

	@Resource(name = "restTemplate2")
	private RestTemplate restTemplate2;

	@Autowired
	private TestService testService;

	@Autowired
	private CircuitBreakerFactory circuitBreakerFactory;

	/**
	 * 测试通过@SentinelResource注解配置进行限流
	 */
	@GetMapping("/hello")
	@SentinelResource("resource")
	public String hello() {
		return "Hello";
	}

	/**
	 * 测试通过@SentinelResource注解配置进行限流
	 * param-flow：热点参数限流
	 */
	@GetMapping("/aa")
	@SentinelResource("aa")
	public String aa(int b, int a) {
		return "Hello test";
	}
	/**
	 * 测试直接通过http请求路径进行限流
	 */
	@GetMapping("/test")
	public String test1() {
		return "Hello test";
	}

	/**
	 * 测试通过@SentinelResource注解配置进行限流，并指定类内blockHandler
	 */
	@GetMapping("/hello2")
	public String hello2(long s) {
		return testService.hello2(s);
	}

	/**
	 * 测试通过@SentinelResource注解配置进行限流，并指定类外blockHandler
	 */
	@GetMapping("/test2")
	public String test2() {
		testService.test2();
		return "test";
	}

	@GetMapping("/template")
	public String client() {
		return restTemplate.getForObject("http://www.taobao.com/test", String.class);
	}

	@GetMapping("/rest1")
	public String rest1() {
		return restTemplate.getForObject("http://localhost:18083/test", String.class);
	}

	/**
	 * 熔断降级：通过 CircuitBreakerFactory 配置
	 * 耗时模拟
	 */
	@GetMapping("/slow")
	public String slow() {
		return circuitBreakerFactory.create("slow").run(() -> {
			try {
				Thread.sleep(500L);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "slow";
		}, throwable -> "fallback");
	}

	/**
	 * 熔断降级：通过@SentinelResource + 规则配置
	 * 耗时模拟，自定义异常处理
	 */
	@GetMapping("/abc0")
	@SentinelResource(value = "abc0", fallback = "degradeException")
	public String abc0() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "abc0";
	}

	/**
	 * 熔断降级：通过@SentinelResource + 规则配置
	 * 耗时模拟，自定义异常处理
	 */
	@GetMapping("/abc1")
	@SentinelResource(value = "abc1", fallback = "degradeException", fallbackClass = ExceptionUtil.class)
	public String abc1() {
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "abc1";
	}

	/**
	 * 自定义熔断降级处理-方式一（资源类内）
	 * 参数和返回必须和资源一致
	 */
	public String degradeException() {
		return "custom degrade info";
	}

}
