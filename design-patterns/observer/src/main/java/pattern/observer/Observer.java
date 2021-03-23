package pattern.observer;

/**
 * 观察者抽象类
 * @author lwq
 * @date 2021/3/23 0023
 */
public abstract class Observer {

	/** 4
	 * 观察者持有被观察者，用以新的观察者注册到被观察者。好让被观察者通知到该新观察者
	 */
	protected Subject subject;

	/**
	 * 观察者接收通知
	 */
	public abstract void update();
}
