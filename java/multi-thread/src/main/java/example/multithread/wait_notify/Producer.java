package example.multithread.wait_notify;

/**
 * @author lwq
 * @date 2020/11/26 0026
 */
public class Producer implements Runnable{
	private QueueBuffer q;

	public Producer(QueueBuffer q) {
		this.q = q;
		new Thread(this, "Producer").start();
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			q.put(i++);
		}
	}
}
