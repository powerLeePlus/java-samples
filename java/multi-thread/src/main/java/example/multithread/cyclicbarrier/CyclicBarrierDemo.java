package example.multithread.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** CyclicBarrier是一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 *
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 *
 * CyclicBarrier类似于CountDownLatch也是个计数器， 不同的是CyclicBarrier数的是调用了CyclicBarrier.await()进入等待的线程数， 当线程数达到了CyclicBarrier初始时规定的数目时，所有进入等待状态的线程被唤醒并继续。
 * CyclicBarrier就象它名字的意思一样，可看成是个障碍， 所有的线程必须到齐后才能一起通过这个障碍。
 *
 * CyclicBarrier初始时还可带一个Runnable的参数，此Runnable任务在CyclicBarrier的数目达到后，所有其它线程被唤醒前被执行。
 *
 * @author lwq
 * @date 2020/12/1 0001
 */
public class CyclicBarrierDemo {

	public static void test1(){
		ExecutorService executorService = Executors.newCachedThreadPool();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3); //创建CyclicBarrier对象并设置3个公共屏障点

		for (int i = 0; i < 3; i++) {
			Runnable runnable = () -> {
				try {
					Thread.sleep((long)(Math.random()*5000));
					System.out.println("线程：" + Thread.currentThread().getName() +
							"，即将到达集合点1，已有：" + cyclicBarrier.getNumberWaiting() +
							"个到达，正在等待。等待总数达到：" + cyclicBarrier.getParties() +
							"将继续前进");
					cyclicBarrier.await();

					Thread.sleep((long)(Math.random()*5000));
					System.out.println("线程：" + Thread.currentThread().getName() +
							"，即将到达集合点2，已有：" + cyclicBarrier.getNumberWaiting() +
							"个到达，正在等待。等待总数达到：" + cyclicBarrier.getParties() +
							"将继续前进");
					cyclicBarrier.await();

					Thread.sleep((long)(Math.random()*5000));
					System.out.println("线程：" + Thread.currentThread().getName() +
							"，即将到达集合点3，已有：" + cyclicBarrier.getNumberWaiting() +
							"个到达，正在等待。等待总数达到：" + cyclicBarrier.getParties() +
							"将继续前进");
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			executorService.submit(runnable);
		}
		executorService.shutdown();
	}

	/**
	 * 如果在构造CyclicBarrier对象的时候传了一个Runnable对象进去，则每次到达公共屏障点的时候都最先执行这个传进去的Runnable，然后再执行处于等待的Runnable。
	 * 把上面的例子改成下面这样：
	 */
	public static void test2(){
		ExecutorService executorService = Executors.newCachedThreadPool();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
			System.out.println("====== 好，3位都到了啊，向下一目标前进 ======");
		}); //创建CyclicBarrier对象并设置3个公共屏障点

		for (int i = 0; i < 3; i++) {
			Runnable runnable = () -> {
				try {
					Thread.sleep((long)(Math.random()*5000));
					System.out.println("线程：" + Thread.currentThread().getName() +
							"，即将到达集合点1，已有：" + cyclicBarrier.getNumberWaiting() +
							"个到达，正在等待。等待总数达到：" + cyclicBarrier.getParties() +
							"将继续前进");
					cyclicBarrier.await();

					Thread.sleep((long)(Math.random()*5000));
					System.out.println("线程：" + Thread.currentThread().getName() +
							"，即将到达集合点2，已有：" + cyclicBarrier.getNumberWaiting() +
							"个到达，正在等待。等待总数达到：" + cyclicBarrier.getParties() +
							"将继续前进");
					cyclicBarrier.await();

					Thread.sleep((long)(Math.random()*5000));
					System.out.println("线程：" + Thread.currentThread().getName() +
							"，即将到达集合点3，已有：" + cyclicBarrier.getNumberWaiting() +
							"个到达，正在等待。等待总数达到：" + cyclicBarrier.getParties() +
							"将继续前进");
					cyclicBarrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
			executorService.submit(runnable);
		}
		executorService.shutdown();
	}

	public static void main(String[] args) {
		// for (int i = 0; i < 100; i++) {
			// 	Random random = new Random();
			// 	int num = random.nextInt(10)*500;
			// 	System.out.println(num);
			// long random = (long)(Math.random()*5000);
			// System.out.println(random);
		// }

		// test1();
		test2();
	}
}
