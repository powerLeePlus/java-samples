package com.lwq.spring.boot.async.web;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


	@GetMapping("test1")
	public Callable<String> test1() throws InterruptedException {
		System.out.println("外层，线程：" + Thread.currentThread().getName());
		Callable<String>  returnCallable = () -> {
			System.out.println("内层，开始处理，线程：" + Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(3);
			System.out.println("内层，结束处理，线程：" + Thread.currentThread().getName());

			return "test1";
		};

		return returnCallable;
	}

}