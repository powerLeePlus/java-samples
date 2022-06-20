package com.lwq.example.multithread.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/** ReentrantLock
 * java除了使用关键字synchronized外，还可以使用ReentrantLock实现独占锁的功能。而且ReentrantLock相比synchronized而言功能更加丰富，使用起来更为灵活，也更适合复杂的并发场景。
 * ReentrantLock常常对比着synchronized来分析，我们先对比着来看然后再一点一点分析。
 * （1）synchronized是独占锁，加锁和解锁的过程自动进行，易于操作，但不够灵活。ReentrantLock也是独占锁，加锁和解锁的过程需要手动进行，不易操作，但非常灵活。
 * （2）synchronized可重入，因为加锁和解锁自动进行，不必担心最后是否释放锁；ReentrantLock也可重入，但加锁和解锁需要手动进行，且次数需一样，否则其他线程无法获得锁。
 * （3）synchronized不可响应中断，一个线程获取不到锁就一直等着；ReentrantLock可以响应中断。
 * （4）ReentrantLock还可以实现公平锁机制。什么叫公平锁呢？也就是在锁上等待时间最长的线程将获得锁的使用权。通俗的理解就是谁排队时间最长谁先执行获取锁。
 *
 * ReentrantLock+Condition 等效于 Synchronized+Object上的wait和notify/notifyAll，可以实现线程间等待通知机制
 * Condition用来使线程等待或者唤醒线程，唤醒的线程是使用同一个condition等待的线程，所以可以唤醒特定的线程。
 * - Condition.signal()：唤醒在条件队列中等待的线程，移动到锁队列（同步队列）（由AQS实现），等待获取锁。实际上此时线程不一定会被唤醒，只有当持有锁的线程调用了await()方法或者unlock()方法后，该线程才会被唤醒。反过来，调用了不调用signal()，其他阻塞等待的线程也不会被唤醒。
 * - Condition.await()：阻塞等待在条件队列中，此时会自动释放ReentrantLock锁
 *
 *
 * @author lwq
 * @date 2020/12/7 0007
 */
public class ReentrantLockDemo {

	private static final ReentrantLock LOCK = new ReentrantLock();

	/**
	 * 最简单的锁
	 */
	private static void test1() {
		LOCK.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " 获得了锁");
			TimeUnit.SECONDS.sleep(2L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			LOCK.unlock();
			System.out.println(Thread.currentThread().getName() + " 释放了锁");
		}
	}

	/**
	 * ReentrantLock + Condition
	 * 示例：两个线程A，B轮流打印1-100
	 * 基本流程 ->
	 * --- lock
	 * --- signal
	 * --- await
	 * --- unlock
	 */
	public static class Task implements Runnable {
		public CountDownLatch countDownLatch = new CountDownLatch(1);
		private int number = 1;
		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		@Override
		// public void run(){
		// 	try {
		// 		countDownLatch.await();
		// 	} catch (InterruptedException e) {
		// 		e.printStackTrace();
		// 	}
		// 	while(number<100){
		// 		System.out.println(Thread.currentThread().getName() + "---准备拿锁");
		//
		// 		lock.lock();
		// 		number++;
		// 		System.out.println(Thread.currentThread().getName() + "---打印：" + number);
		// 		condition.signal();
		// 		try{
		// 			if(number<100)
		// 				condition.await();
		// 		}catch (InterruptedException e) {
		// 			e.printStackTrace();
		// 		}finally{
		// 			lock.unlock();
		// 		}
		// 		System.out.println(Thread.currentThread().getName() + "---锁释放后处理...");
		//
		// 	}
		// }

		public void run() {
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (number <= 100) {
				System.out.println(Thread.currentThread().getName() + "---准备拿锁");
				lock.lock();
				System.out.println(Thread.currentThread().getName() + "---打印：" + number++);
				condition.signal(); // 唤醒其他调用了await的线程
				try {
					if (number <= 100) {
						condition.await(); // 阻塞当前队列并自动释放Lock
						// condition.await(3, TimeUnit.SECONDS);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock(); // 如果这里不释放锁，会导致最后有一个线程一直处于等待获取锁的状态。
				}
				System.out.println(Thread.currentThread().getName() + "---锁释放后处理...");
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + "---最后是否还未释放锁：" + lock.isLocked() + "---是否自己持有锁：" + lock.isHeldByCurrentThread());
			// if (lock.isLocked()) {
			// 	lock.unlock();
			// 	System.out.println(Thread.currentThread().getName() + "---最后释放锁");
			// }
		}
	}

	public static void main(String[] args) {
		/*new Thread(()-> test1(), "线程A").start();
		new Thread(()-> test1(), "线程B").start();*/

		Task task = new Task();
		Thread thread1 = new Thread(task);
		Thread thread2 = new Thread(task);
		thread1.start();
		thread2.start();
		task.countDownLatch.countDown();
		System.out.println("结束");
	}
}
