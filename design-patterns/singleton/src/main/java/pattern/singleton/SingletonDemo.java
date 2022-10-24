package pattern.singleton;

import java.util.concurrent.*;

/**
 * @author lwq
 * @date 2022/10/24 0024
 * @since
 */
public class SingletonDemo {
	
	/**
	 * 1、饿汉式
	 *  类加载时就初始化，浪费内存，不能延迟加载；
	 *  基于 classloader 机制避免了多线程的同步问题，线程安全；
	 *  没有加锁，调用效率高。
	 */
	private static SingletonDemo instance1 = new SingletonDemo();
	public static SingletonDemo getInstance1() {
		return instance1;
	}

	/**
	 * 2、懒汉式（线程不安全）
	 * 是线程不安全的，但是可以做到延迟加载。
	 */
	private static SingletonDemo instance2;
	public static SingletonDemo getInstance2_1() {
		if (instance2 == null) {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			instance2 = new SingletonDemo();
		}
		return instance2;
	}

	/**
	 * 2、懒汉式（线程安全）
	 * 可以确保线程安全，能做到延迟加载，但是效率不高。
	 */
	public synchronized static SingletonDemo getInstance2_2() {
		if (instance2 == null) {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			instance2 = new SingletonDemo();
		}
		return instance2;
	}

	/**
	 * 3、双重检查锁
	 * 可以确保线程安全
	 * 能做到延迟加载
	 * 在加锁之前判断是否为空，可以确保 instance 不为空的情况下，不用加锁，可以直接返回。可以保证效率
	 */
	private volatile static SingletonDemo instance3;
	public static SingletonDemo getInstance3() {
		if (instance3 == null) {
			synchronized (SingletonDemo.class) {
				if (instance3 == null) {
					try {
						TimeUnit.SECONDS.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					instance3 = new SingletonDemo();
				}
			}
		}
		return instance3;
	}

	/**
	 * 4、静态内部类
	 * 利用了classloader机制来保证初始化 instance 时只有一个线程，线程安全；
	 * 只有通过显式调用 getInstance 方法时，才会显式装载静态内部类，从而实例化instance，延迟加载。
	 */
	private static class SingletonDemoInner {
		private static final SingletonDemo INSTANCE = new SingletonDemo();
	}
	public static SingletonDemo getInstance4() {
		return SingletonDemoInner.INSTANCE;
	}

	/**
	 * 5、枚举
	 * 这是实现单例模式的最佳方法。
	 * 它更简洁，不仅能避免多线程同步问题，而且还自动支持序列化机制，防止反序列化重新创建新的对象，绝对防止多次实例化。
	 * 但是不是延迟加载的。
	 */
	public enum  SingletonDemoEnum {
		// 枚举元素本身就是单例
		INSTANCE;

		public SingletonDemoEnum getInstance5() {
			return INSTANCE;
		}
	}

	public static void main(String[] args) {
		int conCurrent = 10;
		CyclicBarrier cyclicBarrier = new CyclicBarrier(conCurrent);
		ExecutorService executorService = Executors.newFixedThreadPool(conCurrent);
		for (int i = 0; i < conCurrent; i++) {
			executorService.execute(
					() -> {
						try {
							cyclicBarrier.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							e.printStackTrace();
						}
						// SingletonDemo singletonDemo = SingletonDemo.getInstance1();
						// SingletonDemo singletonDemo = SingletonDemo.getInstance2_1();
						// SingletonDemo singletonDemo = SingletonDemo.getInstance2_2();
						// SingletonDemo singletonDemo = SingletonDemo.getInstance3();
						// SingletonDemo singletonDemo = SingletonDemo.getInstance4();
						SingletonDemoEnum singletonDemo = SingletonDemoEnum.INSTANCE.getInstance5();
						System.out.println(singletonDemo);
					}
			);
		}
		executorService.shutdown();
	}
}
