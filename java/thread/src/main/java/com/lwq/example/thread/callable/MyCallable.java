package com.lwq.example.thread.callable;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author lwq
 * @date 2022/6/14 0014
 * @since
 */
public class MyCallable implements Callable<Integer> {
	@Override
	public Integer call() throws Exception {
		System.out.println("callable do something");
		Thread.sleep(5000);
		return new Random().nextInt(100);
	}

	/**
	 * Callable + FutureTask + Thread
	 */
	public static void testFutureTask() throws InterruptedException, ExecutionException {
		MyCallable myCallable = new MyCallable();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(myCallable);
		Thread thread = new Thread(futureTask);
		thread.start();
		Thread.sleep(1000);
		// 尝试取消对此任务的执行：该方法如果由于任务已完成、已取消则返回false，如果能够取消还未完成的任务，则返回true
		futureTask.cancel(true);
		// 判断是否在任务正常完成前取消
		System.out.println("future is cancel:" + futureTask.isCancelled());
		if (futureTask.isCancelled()) {
			System.out.println("future is cancelled");
		} else {
			System.out.println("future can't cancelled");
		}
		// 判断任务是否已完成：如果任务完成，则返回true，以下几种情况都属于任务完成：正常终止、异常或者取消而完成。
		System.out.println("future id done:" + futureTask.isDone());
		if (futureTask.isDone() && !futureTask.isCancelled()) {
			System.out.println("future get=" + futureTask.get());
		} else {
			// 任务还未完成
			System.out.println("task is doing or cancelled");
		}
	}

	/**
	 * Callable + ThreadPoolExecutor
	 */
	public static void testFuture() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		MyCallable myCallable = new MyCallable();
		Future<Integer> future = executorService.submit(myCallable);
		executorService.shutdown();
		Thread.sleep(1000);
		System.out.println("主线程休眠5秒，当前时间：" + System.currentTimeMillis());
		// 会阻塞当前线程，直到任务完成
		Integer inte = future.get();
		System.out.println("future已拿到数据:" + inte + ";当前时间：" + System.currentTimeMillis());
	}

	/**
	 * Callable + FutureTask + ThreadPoolExecutor
	 */
	public static void testFuture2() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		MyCallable myCallable = new MyCallable();
		FutureTask<Integer> futureTask = new FutureTask<>(myCallable);
		executorService.submit(futureTask);
		executorService.shutdown();
		Thread.sleep(1000);
		System.out.println("主线程休眠5秒，当前时间：" + System.currentTimeMillis());
		// 会阻塞当前线程，直到任务完成
		Integer inte = futureTask.get();
		System.out.println("future已拿到数据:" + inte + ";当前时间：" + System.currentTimeMillis());
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// testFutureTask();
		// testFuture();
		testFuture2();
	}
}
