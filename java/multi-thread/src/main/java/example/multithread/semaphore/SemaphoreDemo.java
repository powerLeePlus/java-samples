package example.multithread.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/** SemaphoreDemo(信号量)：是一种计数器，用来保护一个或者多个共享资源的访问。
 * Semaphore用于限制可以访问某些资源（物理或逻辑的）的线程数目，他维护了一个许可证集合，有多少资源需要限制就维护多少许可证集合，
 * 假如这里有N个资源，那就对应于N个许可证，同一时刻也只能有N个线程访问。一个线程获取许可证就调用acquire方法，用完了释放资源就调用release方法。
 * 不过这样的解释实在有点抽象，现在用我自己的话来解释一下：
 * 相信在学生时代都去餐厅打过饭，假如有3个窗口可以打饭，同一时刻也只能有3名同学打饭。第四个人来了之后就必须在外面等着，只要有打饭的同学好了，就可以去相应的窗口了。
 *
 * 如果线程要访问一个资源就必须先获得信号量。
 * 如果信号量内部计数器大于0，信号量减1，然后允许共享这个资源；否则，如果信号量的计数器等于0，信号量将会把线程置入休眠直至计数器大于0.
 * 当信号量使用完时，必须释放。
 *
 * @author lwq
 * @date 2020/12/1 0001
 */
public class SemaphoreDemo {

	public static void test1() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		// Semaphore semaphore = new Semaphore(3, true); //3个许可证，是否公平模式
		Semaphore semaphore = new Semaphore(3); //3个许可证，

		for (int i = 0; i < 10; i++) {
			executorService.submit(() -> {
				try {
					System.out.println("线程：" + Thread.currentThread().getName() + "，准备获取许可证，当前可用的许可：" + semaphore.availablePermits());
					semaphore.acquire(); //从信号量获取一个许可，如果无可用许可前将一直阻塞等待

					// do something
					System.out.println("线程：" + Thread.currentThread().getName() + "，拿到了锁，do something，当前可用的许可：" + semaphore.availablePermits());
					TimeUnit.SECONDS.sleep(1);

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					semaphore.release(); //释放一个许可，别忘了在finally中使用，注意：多次调用该方法，会使信号量的许可数增加，达到动态扩展的效果，如：初始permits为1， 调用了两次release，最大许可会改变为2
					System.out.println("线程：" + Thread.currentThread().getName() + "，释放了锁，当前可用的许可：" + semaphore.availablePermits());
				}
			});
		}
		executorService.shutdown();
	}

	/**
	 * 学生窗口打饭
	 */
	public static void test2() {
		// 第一步、定义一个信号量：即3个打饭窗口
		Semaphore semaphore = new Semaphore(3);
		// 第二步、10个学生去打饭
		for (int i = 1; i <= 10; i++) {
			// 第三步、只有3个窗口，学生打饭需要有空闲窗口（信号量有可用许可证）
			new Student(semaphore, "学生" + i).start();
		}

	}

	/**
	 * 打饭的学生
	 */
	public static class Student extends Thread {
		private Semaphore sp;
		private String name;

		public Student(Semaphore sp, String name) {
			this.sp = sp;
			this.name = name;
		}

		@Override
		public void run() {
			try {
				System.out.println(name + "，排队中...");
				sp.acquire();
				System.out.println(name + "，排到了，可以打饭了");
				TimeUnit.SECONDS.sleep(5);
				System.out.println(name + "，打饭中...(5秒)");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				sp.release();
				System.out.println(name + "，打好饭了，释放窗口");
			}
		}
	}

	/** 其他方法
	 * 1、acquire(int permits)
	 *
	 * 从此信号量获取给定数目的许可，在提供这些许可前一直将线程阻塞，或者线程已被中断。就好比是一个学生占两个窗口。这同时也对应了相应的release方法。
	 *
	 * 2、release(int permits)
	 *
	 * 释放给定数目的许可，将其返回到信号量。这个是对应于上面的方法，一个学生占几个窗口完事之后还要释放多少
	 *
	 * 3、availablePermits()
	 *
	 * 返回此信号量中当前可用的许可数。也就是返回当前还有多少个窗口可用。
	 *
	 * 4、reducePermits(int reduction)
	 *
	 * 根据指定的缩减量减小可用许可的数目。
	 *
	 * 5、hasQueuedThreads()
	 *
	 * 查询是否有线程正在等待获取资源。
	 *
	 * 6、getQueueLength()
	 *
	 * 返回正在等待获取的线程的估计数目。该值仅是估计的数字。
	 *
	 * 7、tryAcquire(int permits, long timeout, TimeUnit unit)
	 *
	 * 如果在给定的等待时间内此信号量有可用的所有许可，并且当前线程未被中断，则从此信号量获取给定数目的许可。
	 *
	 * 8、acquireUninterruptibly()
	 *
	 * 从此信号量获取给定数目的许可，在提供这些许可前一直将线程阻塞。与acquire()区别是，acquireUninterruptibly不允许线程被中断
	 */

	public static void main(String[] args) throws InterruptedException {
		// test1();
		test2();
	}
}
