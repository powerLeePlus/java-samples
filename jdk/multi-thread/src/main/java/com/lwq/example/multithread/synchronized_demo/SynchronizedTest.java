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

	/**
	 * jdk1.5后对synchronized的优化：
	 * 自适应自旋锁（不释放CPU资源）；
	 * 锁消除（逃逸分析、JIT、取消同步锁）；
	 * 锁膨胀（无锁->偏向锁->轻量级锁（其中会有锁自旋）->重量级锁）；
	 * 锁粗化（锁的代码段变大）
	 *
	 * 在所有的锁都启用的情况下线程进入临界区时会先去获取偏向锁，如果已经存在偏向锁了，则会尝试获
	 * 取轻量级锁，启用自旋锁，如果自旋也没有获取到锁，则使用重量级锁，没有获取到锁的线程阻塞挂
	 * 起，直到持有锁的线程执行完同步块唤醒他们；
	 *
	 * 偏向锁是在无锁争用的情况下使用的，也就是在当前线程没有执行完之前，没有其它线程会执行
	 * 该同步块，一旦有了第二个线程的争用，偏向锁就会升级为轻量级锁，如果轻量级锁自旋到达阈值后，
	 * 没有获取到锁，就会升级为重量级锁；
	 * 如果线程争用激烈，那么应该禁用偏向锁。
	 */
}
