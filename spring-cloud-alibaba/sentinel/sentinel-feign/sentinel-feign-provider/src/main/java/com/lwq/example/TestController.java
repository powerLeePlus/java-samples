package com.lwq.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lwq
 * @date 2021/6/2 0002
 */
@RestController
public class TestController {
	@GetMapping("/echo/{str}")
	public String echo(@PathVariable String str) {
		return "feign provider echo" + str;
	}
}
