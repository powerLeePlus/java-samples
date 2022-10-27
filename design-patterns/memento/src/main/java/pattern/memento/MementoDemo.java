package pattern.memento;

/**
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class MementoDemo {

	public static void main(String[] args) {
		CareTaker careTaker = new CareTaker();
		Originator originator = new Originator();

		// 状态1
		originator.setState("state#1");
		// 为状态1创建备忘录
		careTaker.add(originator.createMemento());

		// 状态2
		originator.setState("state#2");
		// 为状态2创建备忘录
		careTaker.add(originator.createMemento());

		// 最新状态
		originator.setState("state#3");

		System.out.println("当前状态:" + originator.getState());
		originator.restoreStateFromMemento(careTaker.get(0));
		System.out.println("第一次备忘录状态:" + originator.getState());
		originator.restoreStateFromMemento(careTaker.get(1));
		System.out.println("第二次备忘录状态:" + originator.getState());

	}
}
