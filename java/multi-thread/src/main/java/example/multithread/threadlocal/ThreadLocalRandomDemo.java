package example.multithread.threadlocal;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 多线程下的Random
 * 避免 Random 实例被多线程使用，虽然共享该实例是线程安全的，但会因竞争同一 seed导致的性能下降。
 * 说明：Random 实例包括 java.util.Random 的实例或者 Math.random()的方式。
 * 正例：在 JDK7 之后，可以直接使用 API ThreadLocalRandom，而在 JDK7 之前，需要编码保证每个线程持有一个单独的 Random 实例。
 *
 * @author lwq
 * @date 2020/12/28 0028
 */
public class ThreadLocalRandomDemo {

	public static void main(String[] args) {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

		ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int i = 0; i < 20000; i++) {
			threadPoolExecutor.execute(() -> {
				int r = random.nextInt(10);
				System.out.println(r);
			});

		}
	}
}
