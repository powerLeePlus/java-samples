package com.lwq.example.multithread.synchronized_demo;

/**
 * synchronized对象锁和类锁
 * @author lwq
 * @date 2022/6/17 0017
 * @since
 */
public class SynchronizedTest {

	/**
	 * 对象锁
	 */
	public void test1() throws InterruptedException {
		synchronized (this) {
			System.out.println(Thread.currentThread().getName() + "--- test1 Doing");
			Thread.sleep(2000);
			test2();
		}
	}
	/**
	 * 方法锁（效果等同于对象锁，即：test2和test1效果一样都相当于对象锁）
	 */
	public synchronized void test2() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "---test2 Doing");
		Thread.sleep(2000);
		test1();
	}

	/**
	 * 类锁
	 */
	public void testClass1() throws InterruptedException {
		synchronized (SynchronizedTest.class) {
			System.out.println(Thread.currentThread().getName() + "---testClass1 Doing");
			Thread.sleep(2000);
			testClass2();
		}
	}
	/**
	 * 静态方法锁(效果等同于类锁，即：testClass2和testClass1效果一样都相当于类锁)
	 */
	public static synchronized void testClass2() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "---testClass2 Doing");
		Thread.sleep(2000);
		testClass3();
		// new SynchronizedTest().testClass1();
	}
	public static synchronized void testClass3() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "---testClass3 Doing");
		Thread.sleep(2000);
		testClass2();
	}
	
	
	/**
	 * 死锁演示1：类锁下的死锁
	 */
	public static class E {
		public static synchronized void test() throws InterruptedException {
			System.out.println(Thread.currentThread().getName() + "---E.test Doing");
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() + "---E.test ready to do E1.test");
			E1.test();
		}
	}
	public static class E1 {
		public static synchronized void test() throws InterruptedException {
			System.out.println(Thread.currentThread().getName() + "---E1.test Doing");
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() + "---E1.test ready to do E.test");
			E.test();
		}
	}

	/**
	 * 死锁演示2：对象锁下的死锁
	 */
	public void test1(String s, Object obj) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "---test1 Doing");
		synchronized (s) {
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() + "---test1 s locked, ready to lock obj");
			synchronized (obj) {
				Thread.sleep(2000);
				System.out.println(Thread.currentThread().getName() + "---test1 obj locked");
			}
		}
	}
	public void test2(String s, Object obj) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + "---test2 Doing");
		synchronized (obj) {
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() + "---test2 obj locked, ready to lock s");
			synchronized (s) {
				Thread.sleep(2000);
				System.out.println(Thread.currentThread().getName() + "---test2 s locked");
			}
		}
	}
}
