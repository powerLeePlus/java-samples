package pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者
 * @author lwq
 * @date 2021/3/23 0023
 */
public class Subject {

	/** 1
	 * 被观察者需要持有所有的观察者
	 */
	private List<Observer> observers = new ArrayList<>();

	private Integer state;

	public Integer getState() {
		return state;
	}

	/** 2
	 * 观察者需要观察的被观察者的行为（被观察者需要通知观察者的行为）
	 */
	public void setState(Integer state) {
		this.state = state;
		/** 3
		 * 通知观察者（调用 观察者接收通知 的方法）
		 */
		notifyAllObservers();
	}

	public void notifyAllObservers() {
		/**
		 * 通知到所有观察者
		 */
		for (Observer observer : observers) {
			observer.update();
		}
	}

	/** 5
	 * 提供观察者注册到被观察者的入口
	 */
	public void attach(Observer observer) {
		observers.add(observer);
	}
}
