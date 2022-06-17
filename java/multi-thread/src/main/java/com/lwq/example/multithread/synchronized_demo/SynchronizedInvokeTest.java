package com.lwq.example.multithread.synchronized_demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author lwq
 * @date 2022/6/17 0017
 * @since
 */
public class SynchronizedInvokeTest {

	// 不同对象访问类锁（静态方法锁效果一样）测试
	public static class Client {
		public static void main(String[] args) {
			final CountDownLatch c = new CountDownLatch(1);
			new Thread(() -> {
					SynchronizedTest t1 = new SynchronizedTest();
					try {
						System.out.println(Thread.currentThread().getName() + "启动");
						c.await();
						// t1.testClass3();
						t1.testClass1();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			).start();
			new Thread(() -> {
				SynchronizedTest t1 = new SynchronizedTest();
				try {
					System.out.println(Thread.currentThread().getName() + "启动");
					c.await();
					// t1.testClass2();
					t1.testClass2();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			c.countDown();

			/* 结果：可以发现 静态方法的synchronized（和类锁效果一样），一个线程获得不释放，其他线程就不能访问 通过日志可以看到Thread1 一直在等待Thread0
			Thread-0启动
			Thread-1启动
			Thread-0---testClass3 Doing
			Thread-0---testClass2 Doing
			Thread-0---testClass3 Doing
			Thread-0---testClass2 Doing
			Thread-0---testClass3 Doing
			Thread-0---testClass2 Doing
			*/
		}
	}
}
