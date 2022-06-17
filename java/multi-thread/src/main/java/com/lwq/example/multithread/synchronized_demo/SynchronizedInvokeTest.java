package com.lwq.example.multithread.synchronized_demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author lwq
 * @date 2022/6/17 0017
 * @since
 */
public class SynchronizedInvokeTest {

	public static class Client {

		/**
		 * 不同对象访问类锁（静态方法锁效果一样）测试
		 */
		private static void testClass() {
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

		/**
		 * 直接通过类静态方法访问静态方法锁测试(同{@link #testClass()}效果一样)
		 */
		private static void testClass2() {
			final CountDownLatch c = new CountDownLatch(1);
			new Thread(() -> {
				try {
					System.out.println(Thread.currentThread().getName() + "启动");
					c.await();
					SynchronizedTest.testClass2();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			).start();
			new Thread(() -> {
				try {
					System.out.println(Thread.currentThread().getName() + "启动");
					c.await();
					SynchronizedTest.testClass3();
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

		/**
		 * 不同对象访问对象锁测试
		 */
		public void test() {
			final CountDownLatch c = new CountDownLatch(1);
			new Thread(() -> {
				SynchronizedTest test = new SynchronizedTest();
				System.out.println(Thread.currentThread().getName() + "启动");
				try {
					c.await();
					test.test2();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}).start();
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "启动");
				SynchronizedTest test = new SynchronizedTest();
				try {
					c.await();
					test.test1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
			c.countDown();

			/* 结果：完全没问题。因为是不同对象持有自己的锁
			Thread-1启动
			Thread-0启动
			Thread-0--- test1 Doing
			Thread-1---test2 Doing
			Thread-0---test2 Doing
			Thread-1--- test1 Doing
			Thread-0--- test1 Doing
			*/
		}

		public static void main(String[] args) {
			// testClass();
			// testClass2();

			Client client = new Client();
			client.test();
		}
	}
}
