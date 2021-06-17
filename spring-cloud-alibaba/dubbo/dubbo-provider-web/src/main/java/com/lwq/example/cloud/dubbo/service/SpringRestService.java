package com.lwq.example.cloud.dubbo.service;

import static com.lwq.example.cloud.dubbo.util.LoggerUtils.log;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author lwq
 * @date 2021/6/16 0016
 */
@DubboService(version = "1.0.0")
@RestController
public class SpringRestService implements RestService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@GetMapping("/param")
	public String param(@RequestParam("param") String param) {
		log("/param", param);
		return param;
	}

	@Override
	@PostMapping("/params")
	public String params(@RequestParam("a") int a, @RequestParam("b") String b) {
		log("/params", a + b);
		return a + b;
	}

	@Override
	@GetMapping("/headers")
	public String headers(@RequestHeader("h") String header,
	                      @RequestHeader("h2") String header2, @RequestParam("v") Integer param) {
		String result = header + " , " + header2 + " , " + param;
		log("/headers", result);
		return result;
	}

	@Override
	@GetMapping("/path-variables/{p1}/{p2}")
	public String pathVariables(@PathVariable("p1") String path1,
	                            @PathVariable("p2") String path2, @RequestParam("v") String param) {
		String result = path1 + " , " + path2 + " , " + param;
		log("/path-variables", result);
		return result;
	}

	// @CookieParam does not support : https://github.com/OpenFeign/feign/issues/913
	// @CookieValue also does not support

	@Override
	@PostMapping("/form")
	public String form(@RequestParam("f") String form) {
		return String.valueOf(form);
	}

	@Override
	@PostMapping(value = "/request/body/map", produces = APPLICATION_JSON_VALUE)
	public User requestBodyMap(@RequestBody Map<String, Object> data,
	                           @RequestParam("param") String param) {
		User user = new User();
		user.setId(((Integer) data.get("id")).longValue());
		user.setName((String) data.get("name"));
		user.setAge((Integer) data.get("age"));
		log("/request/body/map", param);
		return user;
	}

	@PostMapping(value = "/request/body/user", consumes = MediaType.APPLICATION_JSON)
	@Override
	public Map<String, Object> requestBodyUser(@RequestBody User user) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", user.getId());
		map.put("name", user.getName());
		map.put("age", user.getAge());
		return map;
	}

}
