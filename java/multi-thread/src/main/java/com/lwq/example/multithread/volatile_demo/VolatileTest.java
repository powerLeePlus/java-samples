package com.lwq.example.multithread.volatile_demo;

import java.util.Random;

/**
 * @author lwq
 * @date 2022/6/15 0015
 * @since
 */
public class VolatileTest {

	/**
	 * 应用1：状态标志
	 */
	public static class VolatileTest1 {
		private static volatile boolean shutdownRequested;

		public void shutdown() {
			shutdownRequested = true;
		}
		public void doWork() {
			while (!shutdownRequested) {
				// do something
				System.out.println("Random:" + new Random().nextInt(100));
			}
		}
	}

	/**
	 * 应用2：一次性安全发布，如双重检查锁
	 */
	public static class VolatileTest2 {
		private volatile static VolatileTest2 instance;

		public static VolatileTest2 getInstance() {
			if (instance == null) {
				synchronized (VolatileTest2.class) {
					if (instance == null) {
						instance = new VolatileTest2();
					}
				}
			}
			return instance;
		}
	}

	/**
	 * 应用3：独立观察
	 */
	public static class VolatileTest3 {
		public volatile String lastUser;

		public boolean authenticate(String user, String password) {
			// do something
			lastUser = user;
			return true;
		}
	}

	/**
	 * 应用4：volatile bean
	 */
	public static class VolatileTest4 {
		private volatile String firstName;
		private volatile String lastName;
		private volatile int age;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}

	/**
	 * 应用5：开销较低的“读-写锁”策略
	 */
	public static class VolatileTest5 {
		private volatile int value;

		//读操作，没有synchronized，提高性能
		public int getValue() {
			return value;
		}
		//写操作，必须synchronized，因为x++不是原子操作
		public synchronized int increment() {
			return value++;
		}
	}

	public static void main(String[] args) {

	}
}
