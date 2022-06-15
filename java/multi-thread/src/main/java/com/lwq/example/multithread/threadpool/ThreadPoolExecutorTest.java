package com.lwq.example.multithread.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lwq
 * @date 2022/6/14 0014
 * @since
 */
public class ThreadPoolExecutorTest {

	//参数初始化
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	//核心线程数
	private static final int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));
	//最大线程书
	private static final int maximumPoolSize = CPU_COUNT * 2 + 1;
	//线程空闲后的存货时长
	private static final int keepAliveTime = 30;
	//任务过多后，存储任务的一个阻塞队列
	BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();
	//线程的创建工厂
	ThreadFactory threadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "AdvancedAsyncTask#" + mCount.getAndIncrement());
		}
	};

	// 线程池任务满载后采取的任务拒绝策略
	RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.DiscardOldestPolicy();

	// 线程池对象，创建线程
	ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, threadFactory, rejectHandler);

	public static void main(String[] args) {
		ThreadPoolExecutorTest threadPoolExecutorTest = new ThreadPoolExecutorTest();
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			threadPoolExecutorTest.mExecutor.execute(() -> {
				try {
					Thread.sleep(100);
					System.out.println("I‘m no." + finalI);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
		threadPoolExecutorTest.mExecutor.shutdown();
		System.out.println("game over");

		/**
		 * SynchronousQueue + DiscardOldestPolicy + i>9
		 * 会抛出：Exception in thread "main" java.lang.StackOverflowError
		 * 且程序无法结束。为啥？？？
		 */
	}
}
