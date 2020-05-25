package com.example.handler;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import lombok.extern.slf4j.Slf4j;

/**
 * kafka consum handler thread factory
 * @author lwq
 * @date 2020/4/24 0024
 */
@Slf4j
public class HandlerThreadFactory implements ThreadFactory {

	private static final String threadNamePrefix = "UserIntegralHandler-pool-";

	private final AtomicInteger poolNum = new AtomicInteger(1);

	private final AtomicInteger threadNum = new AtomicInteger(1);

	private final HandlerThreadGroup handlerThreadGroup;

	public HandlerThreadFactory() {
		String threadGroupName = threadNamePrefix + poolNum.getAndIncrement();
		handlerThreadGroup = new HandlerThreadGroup(threadGroupName);
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(handlerThreadGroup, r,
				handlerThreadGroup.getName() + "-thread-" + threadNum.getAndIncrement());
		return thread;

	}

	private class HandlerThreadGroup extends ThreadGroup {

		public HandlerThreadGroup(String name) {
			super(name);
			log.info("creat handle userIntegral threadGroup, name:{}", name);
		}

		// 新起线程中有异常回到这里
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			super.uncaughtException(t, e);
			log.error("handle userIntegral error，出错了", e);
			// 调用失败处理方法
			//System.out.println("报错了，线程：" + t.getName());
		}
	}

	public class UserIntegralHandlerThread extends Thread {
		private ConsumerRecord consumerRecord;

		public UserIntegralHandlerThread(ThreadGroup group, Runnable target, ConsumerRecord consumerRecord) {
			super(group, target);
			this.consumerRecord = consumerRecord;
		}
	}
}
