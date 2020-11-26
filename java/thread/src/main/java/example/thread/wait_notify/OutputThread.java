package example.thread.wait_notify;

/**
 * @author lwq
 * @date 2020/11/26 0026
 */
public class OutputThread implements Runnable {

	//要打印的1,2。
	private int num;

	private Object lock;

	public OutputThread(int num, Object lock) {
		this.num = num;
		this.lock = lock;
	}

	@Override
	public void run() {
		// 自旋不断执行
		while (true) {
			//必须加锁
			synchronized (lock) {
				try {
					System.out.println("我是：" + num);
					// 要先notify，再wait。否则每个线程就都wait阻塞了。
					lock.notifyAll();
					// 阻塞，等待另一个线程唤醒
					lock.wait();
					System.out.println(num);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
