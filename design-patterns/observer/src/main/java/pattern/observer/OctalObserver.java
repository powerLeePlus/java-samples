package pattern.observer;

/** 观察者实现二
 * @author lwq
 * @date 2021/3/23 0023
 */
public class OctalObserver extends Observer {

	public OctalObserver(Subject subject) {
		this.subject = subject;

		/** 6
		 * 观察者注册到被观察者
		 */
		subject.attach(this);
	}

	@Override
	public void update() {
		System.out.println("Octal String:" + Integer.toOctalString(subject.getState()));
	}
}
