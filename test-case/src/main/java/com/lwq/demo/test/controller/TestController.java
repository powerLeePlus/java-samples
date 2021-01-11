package com.lwq.demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lwq.demo.test.service.TestService;

/**
 * @author lwq
 * @date 2021/1/11 0011
 */
@RestController
@RequestMapping("test")
public class TestController {

	@Autowired
	private TestService testService;

	@GetMapping("/1")
	public String test1() {
		String msg = testService.test1();
		System.out.println(msg);
		return msg;
	}
}
