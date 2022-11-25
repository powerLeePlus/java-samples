package com.lwq.spring.aop.annotation.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lwq.spring.aop.annotation.service.HelloService;


@RequestMapping("/aop")
@RestController
public class HelloController {

	@Autowired
	private HelloService helloService;

	@GetMapping("/test")
	public String test() {
		helloService.depOperation(null);
		helloService.grpOperation("小组长集合", null);
		helloService.memberOperation(null, "战士们冲啊");
		helloService.oneDepOperation(null);
		helloService.oneDepOperation2(null, "战士冲啊");
		return "ok";
	}
}