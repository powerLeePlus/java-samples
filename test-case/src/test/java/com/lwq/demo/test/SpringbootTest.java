package com.lwq.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lwq
 * @date 2021/1/11 0011
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它会去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest(classes = TestApplication.class)
public class SpringbootTest {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void test() {
		System.out.println(applicationContext.getDisplayName());
	}
}
