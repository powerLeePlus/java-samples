package example.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch示例
 * CountDownLatch 可以理解就是个计数器，只能减不能加，同时它还有个门闩的作用，当计数器不为0时，门闩是锁着的；当计数器减到0时，门闩就打开了。
 * @author lwq
 * @date 2020/11/26 0026
 */
public class CountDownLatchDemo {

	// 线程数
	private static final int count = 3;

	public static class MyRunnable implements Runnable {

		// 该线程的序号
		private int num;

		//做线程的同步器
		private CountDownLatch countDownLatch;

		public MyRunnable(int num, CountDownLatch countDownLatch) {
			this.num = num;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {
				System.out.println("第"+ num + "个线程开始执行");
				Thread.sleep(2000);
				System.out.println("第"+ num + "个线程执行结束");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 当前线程执行完了，减1
			countDownLatch.countDown();
		}
	}

	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(count);
		for (int i = 1; i <= count; i++) {
			Thread thread = new Thread(new MyRunnable(i, countDownLatch));
			thread.start();
		}
		// for循环走完就会调用这里
		System.out.println("main thread wait!");
		try {
			// 阻塞当前线程
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//由于countDownLatch.await()，只有等所有for内子线程执行完，并调用countDownLatch.countDown()后才会执行下面代码
		// count个线程调用count次countDown()，countDownLatch计数器正好等于0，门栓打开，线程释放阻塞状态。
		System.out.println("main thread end!");
	}
}
