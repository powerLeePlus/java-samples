package example.multithread.atomic;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 原子性操作
 *
 * 以下操作多线程并发下，不能保证原子性，有线程安全问题
 * int i = 0;
 * i++
 *
 * java.util.concurrent.atomic包下Atomic开头的类可以保证原子性
 * java.util.concurrent.atomic包下Adder结尾的类可以保证原子性，性能较Atomic开头的类更好（减少乐观锁的重试次数）
 *
 * @author lwq
 * @date 2020/12/28 0028
 */
public class AtomicDemo {

	private static AtomicLong atomicLong = new AtomicLong();

	// private static int num;

	private static LongAdder longAdder = new LongAdder();

	public static void testAtomicInteger() {
		long start = System.nanoTime();

		long incrementAndGet = atomicLong.incrementAndGet();
		System.out.println("atomicLong.incrementAndGet耗时：" + (System.nanoTime() - start));
		// System.out.println("andIncrement值：" + incrementAndGet + " , 耗时：" + (System.nanoTime() - start));

		// System.out.println("andIncrement值：" + num++ + " , 耗时：" + (System.nanoTime() - start));

	}

	public static void testLongAdder() {
		long start = System.nanoTime();

		longAdder.increment();
		System.out.println("longAdder.increment耗时：" + (System.nanoTime() - start));

	}

	public static void main(String[] args) throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2000, 2000, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(1));

		for (int i = 0; i < 1000; i++) {
			threadPoolExecutor.execute(() -> {
				testAtomicInteger();
			});
		}

		for (int i = 0; i < 1000; i++) {
			threadPoolExecutor.execute(() -> {
				testLongAdder();
			});
		}

		TimeUnit.SECONDS.sleep(10);
		System.out.println("atomicLong最终值：" + atomicLong.get());
		System.out.println("longAdder最终值：" + longAdder.longValue());
	}
}
