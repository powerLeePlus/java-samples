package example.multithread.wait_notify;

import java.util.Scanner;

/**
 * wait() & notify | notifyAll 示例
 *
 * wait(),notify()和notifyAll()都是java.lang.Object的方法：
 *** wait(): Causes the current thread to wait until another thread invokes the notify() method or the notifyAll() method for this object.
 *** notify(): Wakes up a single thread that is waiting on this object's monitor.
 *** notifyAll(): Wakes up all threads that are waiting on this object's monitor.
 * 这三个方法，都是Java语言提供的实现线程间阻塞(Blocking)和控制进程内调度(inter-process communication)的底层机制。在解释如何使用前，先说明一下两点：
 *** 1. 正如Java内任何对象都能成为锁(Lock)一样，任何对象也都能成为条件队列(Condition queue)。而这个对象里的wait(), notify()和notifyAll()则是这个条件队列的固有(intrinsic)的方法。
 *** 2. 一个对象的固有锁和它的固有条件队列是相关的，为了调用对象X内条件队列的方法，你必须获得对象X的锁。这是因为等待状态条件的机制和保证状态连续性的机制是紧密的结合在一起的。
 * 根据上述两点，在调用wait(), notify()或notifyAll()的时候，必须先获得锁，且状态变量须由该锁保护，而固有锁对象与固有条件队列对象又是同一个对象。也就是说，要在某个对象上执行wait，notify，先必须锁定该对象，而对应的状态变量也是由该对象锁保护的。
 *
 * @author lwq
 * @date 2020/11/26 0026
 */
public class WaitNotifyDemo {

	/**
	 * 探索1：执行wait, notify时，不获得锁会如何？
	 * @author lwq
	 * @date 2020/11/26 0026
	 */
	public static void test1() throws InterruptedException {
		Object obj = new Object();
		obj.wait();
		obj.notify();
	}

	/**
	 * 探索2：执行wait, notify时，不获得该对象的锁会如何？
	 * @author lwq
	 * @date 2020/11/26 0026
	 */
	public static void test2() throws InterruptedException {
		Object lock = new Object();
		Object obj = new Object();
		synchronized (lock) {
			obj.wait();
			obj.notifyAll();
		}
	}

	/**3. 为什么在执行wait, notify时，必须获得该对象的锁？
	 这是因为，如果没有锁，wait和notify有可能会产生竞态条件(Race Condition)。考虑以下生产者和消费者的情景：
		 1.1生产者检查条件（如缓存满了）-> 1.2生产者必须等待
		 2.1消费者消费了一个单位的缓存 -> 2.2重新设置了条件（如缓存没满） -> 2.3调用notifyAll()唤醒生产者

	 我们希望的顺序是： 1.1->1.2->2.1->2.2->2.3
	 但在多线程情况下，顺序有可能是 1.1->2.1->2.2->2.3->1.2。也就是说，在生产者还没wait之前，消费者就已经notifyAll了，这样的话，生产者会一直等下去。

	 所以，要解决这个问题，必须在wait和notifyAll的时候，获得该对象的锁，以保证同步。
	 */

	/**
	 * 案例1：利用wait，notify实现的一个生产者、一个消费者和一个单位的缓存的简单模型：
	 * @author lwq
	 * @date 2020/11/26 0026
	 */
	public static void demo1() {
		QueueBuffer queueBuffer = new QueueBuffer();
		for (int i = 0; i < 3; i++) {
			new Producer(queueBuffer);
			new Consumer(queueBuffer);
		}

		System.out.println("Press double Enter to quit");
		System.out.println();
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		if (s != null) {
			System.exit(-1);
		}
	}

	/**
	 * 案例2：写一个多线程程序，交替输出1,2,1,2,1,2......
	 * @author lwq
	 * @date 2020/11/26 0026
	 */
	public static void demo2() throws InterruptedException {
		// 共用一把锁
		Object lock = new Object();

		new Thread(new OutputThread(1, lock)).start();
		Thread.sleep(5000);
		new Thread(new OutputThread(2, lock)).start();

		// Thread.sleep(100);
		// new Thread(new OutputThread(3, lock)).start();
	}

	public static void main(String[] args) throws InterruptedException {
		// test1();
		// test2();
		demo1();
		// demo2();
	}
}
