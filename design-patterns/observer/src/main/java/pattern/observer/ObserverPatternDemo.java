package pattern.observer;

/**
 * @author lwq
 * @date 2021/3/23 0023
 */
public class ObserverPatternDemo {

	public static void main(String[] args) {
		/** 7
		 * 实例化被观察者
		 */
		Subject subject = new Subject();

		/** 8
		 * 实例化观察者
		 */
		BinaryObserver binaryObserver = new BinaryObserver(subject);
		OctalObserver octalObserver = new OctalObserver(subject);
		HexObserver hexObserver = new HexObserver(subject);

		/**
		 * 被观察者的被观察者行为发生，触发通知到注册到被观察者中的所有观察者，并执行各观察者各自的处理
		 */
		System.out.println("first state change：" + 15);
		subject.setState(15);
		System.out.println("second state change：" + 10);
		subject.setState(10);
	}
}
