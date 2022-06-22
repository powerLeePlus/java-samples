package com.lwq.example.api;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @author lwq
 * @date 2022/6/21 0021
 * @since
 */
public class HashMapTest {
	
	/**
	 * 如果是多线程环境下使用，在一个未知的时间点，会发现CPU占用100%，居高不下，通过查看堆栈，你会惊讶的发现，
	 * 线程都Hang在hashMap的get()方法上，服务重启之后，问题消失，过段时间可能又复现了。
	 * 在并发的情况，发生扩容时，可能会产生循环链表，在执行get的时候，会触发死循环，引起CPU的100%问题，所以一定要避免在并发环境下使用HashMap。
	 */
	static final HashMap<String, String> map = new HashMap<String, String>(2);

	public static void test() throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(10000);
		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					map.put(UUID.randomUUID().toString(), "");
					countDownLatch.countDown();
				}
			}).start();
		}
		countDownLatch.await();
		System.out.println(map.size());
	}

	public static void main(String[] args) throws InterruptedException {
		test();
	}
}
