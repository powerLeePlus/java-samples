package com.lwq.cases.arthas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lwq.cases.arthas.service.TestService;

/**
 * @author lwq
 * @date 2020/10/13 0013
 */
@RestController
@RequestMapping("test")
public class TestController {
	@Autowired
	private TestService testService;

	/**
	 * 模拟执行耗时的操作
	 * @author lwq
	 * @date 2020/10/13 0013
	 * @param second 耗时多久你说了算
	 */
	@GetMapping("/{second}")
	public String test(@PathVariable Integer second){
		try {
			// 这是耗时操作
			String slow = testService.slow(second);
			System.out.println(slow);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "error";
		}
		// 这是正常操作
		String normal = testService.normal();
		System.out.println(normal);
		return normal;
	}

}
