package pattern.state;

/**
 * 状态-具体 开始状态
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class StartState extends State {
	@Override
	public void action(Context context) {
		System.out.println("好戏开场了!");
		// 可以自动切换下一个状态
		// context.setState(new EndState());
	}

	@Override
	public String toString() {
		return "state:start";
	}
}
