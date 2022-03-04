package com.lwq.example.multithread;

import java.util.concurrent.SynchronousQueue;

/**
 * @author lwq
 * @date 2022/3/4 0004
 * @since
 */
public class SynchronousQueueDemo {

	/**
	 * SynchronousQueue
	 * 也是一个队列来的，但它的特别之处在于它内部没有容器，一个生产线程，当它生产产品（即put的时
	 * 候），如果当前没有人想要消费产品(即当前没有线程执行take)，此生产线程必须阻塞，等待一个消费线
	 * 程调用take操作，take操作将会唤醒该生产线程，同时消费线程会获取生产线程的产品（即数据传
	 * 递），这样的一个过程称为一次配对过程(当然也可以先take后put,原理是一样的)。
	 * 我们用一个简单的代码来验证一下，如下所示：
	 */
	public static void main(String[] args) throws InterruptedException {
		SynchronousQueue<Integer> synchronousQueue = new SynchronousQueue<Integer>();
		Thread putThread = new Thread(() -> {
			System.out.println("put thread start");
			try {
				synchronousQueue.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("put thread end");
		});

		Thread takeThread = new Thread(() -> {
			System.out.println("take thread start");
			try {
				Integer take = synchronousQueue.take();
				System.out.println("take from thread:" + take);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("take thread end");
		});
		putThread.start();
		Thread.sleep(3000);
		takeThread.start();
		System.out.println("test end");

		/**
		 * 从结果可以看出，put线程执行queue.put(1) 后就被阻塞了，只有take线程进行了消费，put线程才可以
		 * 返回。可以认为这是一种线程与线程间一对一传递消息的模型。
		 */
	}
}
