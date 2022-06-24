package com.lwq.example.thread.exit;

/**
 * 线程停止的4种方式
 * @author lwq
 * @date 2022/6/23 0023
 * @since
 */
public class ThreadExitDemo {

	public static class myThread2 extends Thread {
		public volatile boolean exit = false;
		@Override
		public void run() {
			while (!exit) {
				System.out.println(Thread.currentThread().getName() + " --- do something");
				try {
					Thread.sleep(100L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " --- 执行完这里就会退出");
		}
	}

	public static class myThread3 extends Thread {
		// 阻塞时抛出中断异常
		// @Override
		// public void run() {
		// 	while (!isInterrupted()) { // 未阻塞判断中断标志退出
		// 		System.out.println(Thread.currentThread().getName() + " --- do something");
		// 		try {
		// 			Thread.sleep(1L);
		// 		} catch (InterruptedException e) { // 阻塞通过中断异常退出
		// 			e.printStackTrace();
		// 			break; // 抛出中断异常不会将中断标志置为true，也即isInterrupted()返回false。所以抛出中断异常需要自己手动退出循环。（许多声明抛出InterruptedException 的方法(如 Thread.sleep(long mills 方法))，抛出异常前，都会清除中断标识位，所以抛出异常后，调用 isInterrupted()方法将会返回 false。）
		// 		}
		// 	}
		// 	System.out.println(Thread.currentThread().getName() + " --- 执行完这里就会退出");
		// }

		// 未阻塞时中断标志变化
		public void run() {
			while (!isInterrupted()) { // 未阻塞判断中断标志退出
				System.out.println(Thread.currentThread().getName() + " --- do something");
			}
			System.out.println(Thread.currentThread().getName() + " --- 执行完这里就会退出");
		}
	}
	
	/**
	 * 方式1：程序运行结束，线程自动结束
	 * - run方法执行完毕
	 * - 抛出异常导致程序退出
	 */
	public static void test1() {
		Thread thread = new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " --- do something");
		});
		thread.start();
	}
	/**
	 * 方式2：使用退出标志退出线程
	 * 一般是自定义的标志属性
	 */
	public static void test2() throws InterruptedException {
		myThread2 thread2 = new myThread2();
		thread2.start();
		Thread.sleep(500L);
		thread2.exit = true;
	}
	
	/**
	 * 方式3：使用Thread的interrupt方法
	 * 使用 interrupt()方法来中断线程有两种情况：
	 * 1.线程处于阻塞状态：如使用了 sleep,同步锁的 wait,socket 中的 receiver,accept 等方法时，会使线
	 * 程处于阻塞状态。当调用线程的 interrupt()方法时，会抛出 InterruptException 异常。阻塞中的那
	 * 个方法抛出这个异常，通过代码捕获该异常，然后 break 跳出循环状态，从而让我们有机会结束这
	 * 个线程的执行。通常很多人认为只要调用 interrupt 方法线程就会结束，实际上是错的， 一定要先
	 * 捕获 InterruptedException 异常之后通过 break 来跳出循环，才能正常结束 run 方法。
	 * 2.线程未处于阻塞状态：使用 isInterrupted()判断线程的中断标志来退出循环。当使用interrupt()方
	 * 法时，中断标志就会置 true，和使用自定义的标志来控制循环是一样的道理。
	 * 简单来讲就是：阻塞状态通过捕获中断异常退出；非阻塞状态通过判断中断标志退出
	 */
	public static void test3() throws InterruptedException {
		myThread3 thread3 = new myThread3();
		thread3.start();
		Thread.sleep(5L);
		thread3.interrupt();
		System.out.println(Thread.currentThread().getName() + "子线程调用interrupt后操作");
	}
	
	/**
	 * 方式4：stop方法终止线程（线程不安全，不建议用）
	 * 程序中可以直接使用 thread.stop()来强行终止线程，但是 stop 方法是很危险的，就象突然关闭计算机
	 * 电源，而不是按正常程序关机一样，可能会产生不可预料的结果，不安全主要是：thread.stop()调用之
	 * 后，创建子线程的线程就会抛出 ThreadDeatherror 的错误，并且会释放子线程所持有的所有锁。一般
	 * 任何进行加锁的代码块，都是为了保护数据的一致性，如果在调用thread.stop()后导致了该线程所持有
	 * 的所有锁的突然释放(不可控制)，那么被保护数据就有可能呈现不一致性，其他线程在使用这些被破坏的
	 * 数据时，有可能导致一些很奇怪的应用程序错误。因此，并不推荐使用 stop 方法来终止线程。
	 */
	public static void test4() throws InterruptedException {
		myThread2 thread2 = new myThread2();
		thread2.start();
		Thread.sleep(500L);
		thread2.exit = true;
		thread2.stop();
		System.out.println(Thread.currentThread().getName() + "子线程调用interrupt后操作");
	}

	public static void main(String[] args) throws InterruptedException {
		// test1();
		// test2();
		// test3();
		test4();
	}
}
