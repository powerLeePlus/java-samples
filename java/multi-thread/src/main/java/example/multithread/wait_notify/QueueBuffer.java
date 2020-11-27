package example.multithread.wait_notify;

/**
 * @author lwq
 * @date 2020/11/26 0026
 */
public class QueueBuffer {
	//计数
	int n;
	// 生产者生产了，记为true。消费者消费了，记为false。消费者不消费库存，就不再生产（保持库存为1）
	boolean valueSet = false;

	/**
	 * 消费者调
	 */
	synchronized int get() {
		System.out.println("Prepare Got: " + n + "......");
		if (!valueSet) {
			try {
				// 生产者还没生产（没调put()），不会把valueSet置为true。就让消费者阻塞，不再消费
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Got: " + n);
		valueSet = false;
		this.notify();
		return n;
	}

	/**
	 * 生产者调
	 */
	synchronized void put(int n) {
		System.out.println("Prepare put: " + n + "......");
		if (valueSet) {
			try {
				// 消费者没消费（没调get()），不会把valueSet置为false。就让生产者阻塞，不再生产
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.n = n;
		valueSet = true;
		System.out.println("Put: " + n);
		notify();
	}
}
