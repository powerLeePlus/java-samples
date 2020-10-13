package com.lwq.cases.arthas.service;

import org.springframework.stereotype.Service;

/**
 * @author lwq
 * @date 2020/10/13 0013
 */
@Service
public class TestService {

	/**
	 * 模拟耗时操作
	 * @author lwq
	 * @date 2020/10/13 0013
	 * @param second 耗时多久你说了算
	 */
	public String slow(Integer second) throws InterruptedException {
		System.out.println("这里是执行耗时的业务，start");
		// 模拟执行耗时的操作
		if (second != null) {
			Thread.sleep(second*1000);
		}

		System.out.println("这里是执行耗时的业务，end");
		return "ok,过了大约" + second + "秒";
	}

	/**
	 * 正常执行的操作
	 * @author lwq
	 * @date 2020/10/13 0013
	 */
	public String normal() {
		System.out.println("这里是正常的业务，start");
		System.out.println("这里是正常的业务，start");
		return "ok";
	}

}
