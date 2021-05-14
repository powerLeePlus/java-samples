package com.lwq.rabbitmq.delayqueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lwq
 * @date 2020/7/8 0008
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DelayQueueApplicationTests {

	@Autowired
	private DelayQueueService delayQueueService;

	@Test
	public void testDelayQueue() {
		delayQueueService.sendDelayMessage("测试延迟");
	}
}
