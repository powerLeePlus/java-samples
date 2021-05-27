package com.lwq.example;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.lwq.example.ConsumerApplication.EchoService;

/**
 * @author lwq
 * @date 2021/5/26 0026
 */
@RestController
public class TestController {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private RestTemplate restTemplate1;

	@Autowired
	private EchoService echoService;
	@Autowired
	private DiscoveryClient discoveryClient;

	@PostConstruct
	public void init() {
		restTemplate1.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) {
				return false;
				// return true; // return true，handleError()方法才起作用
			}

			@Override
			public void handleError(ClientHttpResponse response) {
				System.err.println("handle error");
			}
		});
	}

	/**
	 * RestTemplate方式调用
	 */
	@GetMapping("/echo-rest/{str}")
	public String rest(@PathVariable("str") String str) {
		return restTemplate.getForObject("http://service-provider/echo/" + str, String.class);
	}

	/**
	 * RestTemplate方式调用，Sentinal熔断
	 */
	@GetMapping("/index")
	public String index() {
		return restTemplate1.getForObject("http://service-provider", String.class);
	}

	@GetMapping("/test")
	public String test() {
		return restTemplate1.getForObject("http://service-provider/test", String.class);
	}

	@GetMapping("/sleep")
	public String sleep() {
		return restTemplate1.getForObject("http://service-provider/sleep", String.class);
	}

	/**
	 * Fegin Client调用
	 */
	@GetMapping("/echo-fegin/{str}")
	public String echoFegin(@PathVariable("str") String str) {
		return echoService.echo(str);
	}

	@GetMapping("fegin-divide")
	public String divide(@RequestParam Integer a, @RequestParam Integer b) {
		return echoService.divide(a, b);
	}

	@GetMapping("fegin-divide1")
	public String divide1(@RequestParam Integer a) {
		return echoService.divide(a);
	}

	@GetMapping("/notFound")
	public String notFound() {
		return echoService.notFound();
	}

	/**
	 * discoveryClient
	 */
	@GetMapping("/services/{service}")
	public Object client(@PathVariable("service") String service) {
		return discoveryClient.getInstances(service);
	}

	@GetMapping("/services")
	public Object services() {
		return discoveryClient.getServices();
	}
}
