package com.lwq.example.multithread.aqs;

/**
 * AQS
 *
 ** 定义：AQS(AbstractQueuedSynchronizer)是一套定义多线程访问共享资源的同步器框架
 ** 应用：许多同步实现都依赖于它，如ReentrantLock、Semaphore、CountDownLatch等
 ** 实现：volatile int state(表示共享资源)+内置的FIFO线程等待队列
 ** 自定义实现：
 ** - 独占式：只需要实现tryAcquire(int)和 tryRelease(int)，并在其中完成对state的加和减，，可选实现： isHeldExclusively()
 ** - 共享式：只需要实现tryAcquireShared(int)和 tryReleaseShared(int)，并在其中完成对state的加和减
 *
 *
 * @author lwq
 * @date 2022/6/27 0027
 * @since
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 示例：利用AQS来实现一个不可重入的互斥锁实现。锁资源（AQS里的state）只有两种状态：0表示未锁定，1表示锁定
 */
public class MyAbstractQueuedSynchronizer {

	/**
	 * 静态内部类，自定义同步器
	 */
	private static class Sync extends AbstractQueuedSynchronizer {
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1; // 是否有资源可用
		}

		@Override
		protected boolean tryAcquire(int acquires) {
			assert acquires == 1;
			if (compareAndSetState(0, 1)) { // state: 0-> 代表获取锁
				setExclusiveOwnerThread(Thread.currentThread()); // 设置当前占用资源的线程
				return true;
			}
			return false;
		}

		@Override
		protected boolean tryRelease(int releases) {
			assert releases == 1;
			if (getState() == 0) {
				throw new IllegalMonitorStateException();
			}
			setExclusiveOwnerThread(null);
			setState(0); // state: 1->0 代表释放锁
			return true;
		}

	}

	private final Sync sync = new Sync();

	/**
	 * 获取锁，可能会阻塞
	 */
	public void lock() {
		sync.acquire(1);
	}
	/**
	 * 尝试获取锁，无论成功或失败，立即返回
	 */
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}
	/**
	 * 释放锁
	 */
	public void unlock() {
		sync.release(1);
	}

	/**
	 * 同步类在实现时一般都将自定义同步器（sync）定义为内部类，供自己使用；而同步类自己（MyAbstractQueuedSynchronizer）则实现某个接口，对外服务。当然，接口的实现要直接依赖sync，它们在语义上也存在某种对应关系。而sync只用实现资源state的获取-释放方式tryAcquire-tryRelelase，至于线程的排队、等待、唤醒等，上层的AQS都已经实现好了，我们不用关心。
	 *
	 * ReentrantLock/CountDownLatch/Semphore这些同步类的实现方式都差不多，不同的地方就在获取、释放资源的方式tryAcquire-tryRelelase。
	 */

	public static void main(String[] args) {
		MyAbstractQueuedSynchronizer synchronizer = new MyAbstractQueuedSynchronizer();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10; i++) {
			executorService.execute(() -> {
				System.out.println(Thread.currentThread().getName() + "---准备获取锁");
				try {
					synchronizer.lock();
					// while (!synchronizer.tryLock()) {
					// 	// 自旋获取锁
					// }
					System.out.println(Thread.currentThread().getName() + "---获取锁");
					Thread.sleep(100L);
					System.out.println(Thread.currentThread().getName() + "---do something");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					synchronizer.unlock();
					System.out.println(Thread.currentThread().getName() + "---释放锁");
				}
			});
		}
		executorService.shutdown();
	}
}
