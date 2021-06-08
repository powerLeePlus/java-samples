package com.lwq.example;

import static com.lwq.example.BusinessServiceApplication.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.seata.spring.annotation.GlobalTransactional;

/**
 * @author lwq
 * @date 2021/6/8 0008
 */
@RestController
public class HomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	private static final String SUCCESS = "SUCCESS";

	private static final String FAIL = "FAIL";

	/**
	 * 用户ID
	 */
	private static final String USER_ID = "U100001";

	/**
	 * 货物标识
	 */
	private static final String COMMODITY_CODE = "C00321";

	/**
	 * 每单货物数量
	 */
	private static final int ORDER_COUNT = 2;

	private final RestTemplate restTemplate;

	private final OrderService orderService;

	private final StorageService storageService;

	public HomeController(RestTemplate restTemplate, OrderService orderService,
	                      StorageService storageService) {
		this.restTemplate = restTemplate;
		this.orderService = orderService;
		this.storageService = storageService;
	}

	/**
	 * seata分布式事务使用：restTemplate方式
	 * 全局事务开启
	 */
	@GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
	@GetMapping(value = "/seata/rest", produces = "application/json")
	public String rest() {
		String result = restTemplate.getForObject(
				"http://127.0.0.1:18082/storage/" + COMMODITY_CODE + "/" + ORDER_COUNT,
				String.class);

		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		String url = "http://127.0.0.1:18083/order";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("userId", USER_ID);
		map.add("commodityCode", COMMODITY_CODE);
		map.add("orderCount", ORDER_COUNT + "");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				map, headers);

		ResponseEntity<String> response;
		try {
			response = restTemplate.postForEntity(url, request, String.class);
		}
		catch (Exception exx) {
			throw new RuntimeException("mock error");
		}
		result = response.getBody();
		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		return SUCCESS;
	}

	/**
	 * seata分布式事务使用：feign方式
	 * 全局事务开启
	 */
	@GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
	@GetMapping(value = "/seata/feign", produces = "application/json")
	public String feign() {
		String result = storageService.echo(COMMODITY_CODE, ORDER_COUNT);
		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		result = orderService.order(USER_ID, COMMODITY_CODE, ORDER_COUNT);
		if (!SUCCESS.equals(result)) {
			throw new RuntimeException();
		}

		return SUCCESS;

	}
}
