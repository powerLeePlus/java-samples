package pattern.state;

/**
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class StateDemo {
	public static void main(String[] args) {

		// 开始状态
		StartState startState = new StartState();
		Context context = new Context(startState);
		context.action();
		System.out.println(context.getState());

		// 结束状态
		EndState endState = new EndState();
		context.setState(endState);
		context.action();
		System.out.println(context.getState());
	}
}
