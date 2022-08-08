package com.lwq.spring.ioc.circular_reference;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lwq
 * @date 2022/8/8 0008
 * @since
 */
public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 获取原型bean时，必须调用getBean()，因为原型bean只有在调用getBean()的时候才会实例化对象
		System.out.println(applicationContext.getBean("a", StudentA.class));
	}
}
