package com.lwq.example.multithread.exchanger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;

/**
 * Exchanger用于线程协作，线程间进行数据交换
 * @author lwq
 * @date 2022/6/21 0021
 * @since
 */
public class ExchangerTest {
	private static Exchanger<String> exchanger = new Exchanger<>();
	public static void test() {
		Thread thread1 = new Thread(() -> {
			System.out.println("A Doing");
			try {
				String msg = exchanger.exchange("From A");
				System.out.println("A Task --- " + msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "A");
		Thread thread2 = new Thread(() -> {
			System.out.println("B Doing");
			try {
				String msg = exchanger.exchange("From B");
				System.out.println("B Task --- " + msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}, "B");
		thread1.start();
		thread2.start();

	}

	/**
	 * 子线程与主线程间交换数据
	 */
	public static void test2() throws InterruptedException {
		Thread childThread = new Thread(() -> {
			System.out.println("子线程 Doing");
			try {
				String msg = exchanger.exchange("子线程数据");
				System.out.println("子线程 --- " + msg);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(childThread);
		System.out.println("主线程 Doing");
		String msg = exchanger.exchange("主线程数据");
		System.out.println("主线程 --- " + msg);
		// completableFuture.join();
	}

	public static void main(String[] args) throws InterruptedException {
		// test();
		test2();
	}
}
