package com.lwq.example.multithread.join;

import java.util.concurrent.TimeUnit;

/**
 * @author lwq
 * @date 2022/6/20 0020
 * @since
 */
public class ThreadJoinTest {

	/**
	 * join作用：
	 * 作用是阻塞调用该方法的当前线程，直到被调用线程执行完，才继续执行当前线程；
	 *
	 */
	public static void test() {
		Thread thread = new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + "---i=" + i);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		try {
			System.out.println(Thread.currentThread().getName() + "---join");
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "---join后操作");
	}

	public static void main(String[] args) {
		test();
	}
}
