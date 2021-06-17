package com.lwq.example.cloud.dubbo;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.alibaba.cloud.dubbo.annotation.DubboTransported;
import com.lwq.example.cloud.dubbo.service.RestService;
import com.lwq.example.cloud.dubbo.service.User;
import com.lwq.example.cloud.dubbo.service.UserService;

/**
 * @author lwq
 * @date 2021/6/16 0016
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableFeignClients
@EnableScheduling
@EnableCaching
public class DubboConsumerBootstrap {

	@DubboReference
	private UserService userService;

	@DubboReference(version = "1.0.0", protocol = "dubbo")
	private RestService restService;

	@Autowired
	@Lazy
	private FeignRestService feignRestService;

	@Autowired
	@Lazy
	private DubboFeignRestService dubboFeignRestService;

	@Value("${provider.application.name}")
	private String providerApplicationName;

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		new SpringApplicationBuilder(DubboConsumerBootstrap.class)
				.properties("spring.profiles.active=nacos").run(args);
	}

	@Bean
	public ApplicationRunner userServiceRunner() {
		return arguments -> {

			User user = new User();
			user.setId(1L);
			user.setName("小马哥");
			user.setAge(33);

			// save User
			System.out.printf("UserService.save(%s) : %s\n", user,
					userService.save(user));

			// find all Users
			System.out.printf("UserService.findAll() : %s\n", user,
					userService.findAll());

			// remove User
			System.out.printf("UserService.remove(%d) : %s\n", user.getId(),
					userService.remove(user.getId()));

		};
	}

	@Bean
	public ApplicationRunner callRunner() {
		return arguments -> {
			callAll();
		};
	}

	private void callAll() {

		// To call /path-variables
		callPathVariables();

		// To call /headers
		callHeaders();

		// To call /param
		callParam();

		// To call /params
		callParams();

		// To call /request/body/map
		callRequestBodyMap();

		// To call /request/body/user
		callRequestBody();
	}

	@Scheduled(fixedDelay = 10 * 1000L)
	public void onScheduled() {
		callAll();
	}

	private void callPathVariables() {
		// Dubbo Service call
		System.out.println("restService.pathVariables : " + restService.pathVariables("a", "b", "c"));
		// Spring Cloud Open Feign REST Call (Dubbo Transported)
		System.out.println("dubboFeignRestService.pathVariables : " + dubboFeignRestService.pathVariables("c", "b", "a"));
		// Spring Cloud Open Feign REST Call
		System.out.println("feignRestService.pathVariables : " + feignRestService.pathVariables("b", "a", "c"));

		// RestTemplate call
		System.out.println("restTemplate -> path-variables : " + restTemplate.getForEntity(
				"http://" + providerApplicationName + "//path-variables/{p1}/{p2}?v=c",
				String.class, "a", "b"));
	}

	private void callHeaders() {
		// Dubbo Service call
		System.out.println("restService.headers : " + restService.headers("a", "b", 10));
		// Spring Cloud Open Feign REST Call (Dubbo Transported)
		System.out.println("dubboFeignRestService.headers : " + dubboFeignRestService.headers("b", 10, "a"));
		// Spring Cloud Open Feign REST Call
		System.out.println("feignRestService.headers : " + feignRestService.headers("b", "a", 10));
	}

	private void callParam() {
		// Dubbo Service call
		System.out.println("restService.param : " + restService.param("mercyblitz"));
		// Spring Cloud Open Feign REST Call (Dubbo Transported)
		System.out.println("dubboFeignRestService.param : " + dubboFeignRestService.param("mercyblitz"));
		// Spring Cloud Open Feign REST Call
		System.out.println("feignRestService.param : " + feignRestService.param("mercyblitz"));
	}

	private void callParams() {
		// Dubbo Service call
		System.out.println("restService.params : " + restService.params(1, "1"));
		// Spring Cloud Open Feign REST Call (Dubbo Transported)
		System.out.println("dubboFeignRestService.params : " + dubboFeignRestService.params("1", 1));
		// Spring Cloud Open Feign REST Call
		System.out.println("feignRestService.params : " + feignRestService.params(1,"1"));

		// RestTemplate call
		System.out.println("restTemplate -> param : " + restTemplate.getForEntity(
				"http://" + providerApplicationName + "/param?param=小马哥", String.class));
	}

	private void callRequestBodyMap() {

		Map<String, Object> data = new HashMap<>();
		data.put("id", 1);
		data.put("name", "小马哥");
		data.put("age", 33);

		// Dubbo Service call
		System.out.println("restService.requestBodyMap : " + restService.requestBodyMap(data, "Hello,World"));
		// Spring Cloud Open Feign REST Call (Dubbo Transported)
		System.out.println("dubboFeignRestService.requestBodyMap : " + dubboFeignRestService.requestBodyMap("Hello,World", data));
		// Spring Cloud Open Feign REST Call
		System.out.println("feignRestService.requestBodyMap : " + feignRestService.requestBodyMap(data, "Hello,World"));

		// RestTemplate call
		System.out.println("restTemplate -> /request/body/map : " + restTemplate.postForObject(
				"http://" + providerApplicationName + "/request/body/map?param=小马哥", data,
				User.class));
	}

	private void callRequestBody() {

		User user = new User();
		user.setId(1L);
		user.setName("小马哥");
		user.setAge(22);

		// Dubbo Service call
		System.out.println("restService.requestBodyUser : " + restService.requestBodyUser(user));
		// Spring Cloud Open Feign REST Call (Dubbo Transported)
		System.out.println("dubboFeignRestService.requestBodyUser : " + dubboFeignRestService.requestBodyUser(user));
		// Spring Cloud Open Feign REST Call
		System.out.println("feignRestService.requestBodyUser : " + feignRestService.requestBodyUser(user));

		// RestTemplate call
		System.out.println("restTemplate -> /request/body/user : " + restTemplate.postForObject(
				"http://" + providerApplicationName + "/request/body/user?param=小马哥", user,
				User.class));
	}

	@Bean
	@LoadBalanced
	@DubboTransported
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@FeignClient(value = "${provider.application.name}")
	public interface FeignRestService {
		@GetMapping("/param")
		String param(@RequestParam("param") String param);

		@PostMapping("/params")
		String params(@RequestParam("a") int a, @RequestParam("b") String b);

		@GetMapping("/headers")
		String headers(@RequestHeader("h") String header,
		                      @RequestHeader("h2") String header2, @RequestParam("v") Integer param);

		@GetMapping("/path-variables/{p1}/{p2}")
		String pathVariables(@PathVariable("p1") String path1,
		                            @PathVariable("p2") String path2, @RequestParam("v") String param);

		@PostMapping(value = "/request/body/map", produces = APPLICATION_JSON_VALUE)
		User requestBodyMap(@RequestBody Map<String, Object> data,
		                           @RequestParam("param") String param);

		@PostMapping(value = "/request/body/user", consumes = MediaType.APPLICATION_JSON)
		Map<String, Object> requestBodyUser(User user);

	}

	@FeignClient("${provider.application.name}")
	@DubboTransported(protocol = "dubbo")
	public interface DubboFeignRestService {
		@GetMapping("/param")
		String param(@RequestParam("param") String param);

		@PostMapping("/params")
		String params(@RequestParam("b") String b, @RequestParam("a") int a);

		@GetMapping("/headers")
		String headers(@RequestHeader("h") String header,
		               @RequestParam("v") Integer param, @RequestHeader("h2") String header2);

		@GetMapping("/path-variables/{p1}/{p2}")
		String pathVariables(@PathVariable("p1") String path1,
		                     @PathVariable("p2") String path2, @RequestParam("v") String param);

		@PostMapping(value = "/request/body/map", produces = APPLICATION_JSON_VALUE)
		User requestBodyMap(@RequestParam("param") String param,
		                    @RequestBody Map<String, Object> data);

		@PostMapping(value = "/request/body/user", consumes = MediaType.APPLICATION_JSON)
		Map<String, Object> requestBodyUser(User user);
	}

}
