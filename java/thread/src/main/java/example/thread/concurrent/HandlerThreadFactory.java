package example.thread.concurrent;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * kafka consum handler thread factory
 *
 * @author lwq
 * @date 2020/4/24 0024
 * @copyright copyright (c) 2020
 * @company www.duia.com
 */
@Slf4j
public class HandlerThreadFactory implements ThreadFactory {

	private static final String threadNamePrefix = "sync-pool-";

	private static final AtomicInteger poolNum = new AtomicInteger(1);

	private final AtomicInteger threadNum = new AtomicInteger(1);

	private final HandlerThreadGroup handlerThreadGroup;

	public HandlerThreadFactory(String threadPrefix) {
		if (StringUtils.isEmpty(threadPrefix)) {
			threadPrefix = threadNamePrefix;
		}
		String threadGroupName = threadPrefix + poolNum.getAndIncrement();
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
			log.info(">>>> creat repair threadGroup, name:{}", name);
		}

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			log.error(">>>> repair error，Thread: " + t.getName() + " , Exception: ", e);
		}
	}

}
