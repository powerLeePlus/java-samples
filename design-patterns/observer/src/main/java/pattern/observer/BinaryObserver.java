package pattern.observer;

/** 观察者实现一
 * @author lwq
 * @date 2021/3/23 0023
 */
public class BinaryObserver extends Observer {

	public BinaryObserver(Subject subject) {
		this.subject = subject;

		/** 6
		 * 观察者注册到被观察者
		 */
		subject.attach(this);
	}

	@Override
	public void update() {
		System.out.println("Binary String:" + Integer.toBinaryString(subject.getState()));
	}
}
