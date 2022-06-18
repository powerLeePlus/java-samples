package com.lwq.example.multithread.lock;

import java.util.concurrent.TimeUnit;
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
	 */
	private static void test2() {
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

	public static void main(String[] args) {
		new Thread(()-> test1(), "线程A").start();
		new Thread(()-> test1(), "线程B").start();

	}
}
