package example.multithread.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 双重检查锁
 *
 * @author lwq
 * @date 2020/12/28 0028
 */
public class DoubleCheckLock {

	private static volatile DoubleCheckLock instance;

	private DoubleCheckLock() {
	}

	public static DoubleCheckLock GetInstance() {
		// first check
		if (instance == null) {
			synchronized (DoubleCheckLock.class) {
				if (instance == null) {
					instance = new DoubleCheckLock();
				}
			}
		}

		return instance;
	}

	public static void main(String[] args) {
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1000, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

		for (int i = 0; i < 1000; i++) {
			threadPoolExecutor.execute(() -> {
				System.out.println(DoubleCheckLock.GetInstance());
			});
		}
	}
}
