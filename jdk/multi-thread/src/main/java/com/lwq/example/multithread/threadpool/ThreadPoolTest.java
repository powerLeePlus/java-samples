package com.lwq.example.multithread.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * JUC ThreadPoolExecutor测试
 * @author lwq
 * @date 2022/6/13 0013
 * @since
 */
public class ThreadPoolTest implements Runnable {
	@Override
	public void run() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(5);
		// RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, queue);
		// ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, queue, handler);
		// threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		for (int i = 0; i < 16; i++) {
			threadPoolExecutor.execute(new Thread(new ThreadPoolTest(), "Thread".concat(i + "")));
			System.out.println("线程池中活跃的线程数：" + threadPoolExecutor.getPoolSize());
			if (queue.size() > 0) {
				System.out.println("-----------------------队列中阻塞的线程数：" + queue.size());
			}
		}
		threadPoolExecutor.shutdown();

		/* 执行结果：
		线程池中活跃的线程数：1
		线程池中活跃的线程数：2
		线程池中活跃的线程数：3
		线程池中活跃的线程数：4
		线程池中活跃的线程数：5
		线程池中活跃的线程数：5
				-----------------------队列中阻塞的线程数：1
		线程池中活跃的线程数：5
				-----------------------队列中阻塞的线程数：2
		线程池中活跃的线程数：5
				-----------------------队列中阻塞的线程数：3
		线程池中活跃的线程数：5
				-----------------------队列中阻塞的线程数：4
		线程池中活跃的线程数：5
				-----------------------队列中阻塞的线程数：5
		线程池中活跃的线程数：6
				-----------------------队列中阻塞的线程数：5
		线程池中活跃的线程数：7
				-----------------------队列中阻塞的线程数：5
		线程池中活跃的线程数：8
				-----------------------队列中阻塞的线程数：5
		线程池中活跃的线程数：9
				-----------------------队列中阻塞的线程数：5
		线程池中活跃的线程数：10
				-----------------------队列中阻塞的线程数：5
		Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task Thread[Thread15,5,main] rejected from java.util.concurrent.ThreadPoolExecutor@5674cd4d[Running, pool size = 10, active threads = 10, queued tasks = 5, completed tasks = 0]
		at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2047)
		at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:823)
		at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1369)
		at com.lwq.example.multithread.threadpool.ThreadPoolTest.main(ThreadPoolTest.java:27)
		*/

		/**
		 * 分析
		 * 创建的线程池的具体配置为：核心线程5个，最大线程10个，队列长度5个
		 * 由结果可知；
		 * 刚开始时，会直接创建新的核心线程时，直到达到5个核心线程；
		 * 当达到5个核心线程时，会将任务放入队列中，直到队列中任务数达到5个；
		 * 当队列中任务数达到5个时，会创建新的普通线程，知道总线程数达到10个；
		 * 当总线程数达到10个时，新来任务无法再执行，会通过拒绝策略来进行相应处理（本例未指定拒绝策略，默认为AbortPolicy为直接抛出异常）
		 */

	}
}
