package pattern.observer;

/** 观察者实现三
 * @author lwq
 * @date 2021/3/23 0023
 */
public class HexObserver extends Observer {

	public HexObserver(Subject subject) {
		this.subject = subject;

		/** 6
		 * 观察者注册到被观察者
		 */
		subject.attach(this);
	}

	@Override
	public void update() {
		System.out.println("Hex String:" + Integer.toHexString(subject.getState()));
	}
}
