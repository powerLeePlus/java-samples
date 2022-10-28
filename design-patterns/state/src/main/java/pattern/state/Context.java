package pattern.state;

/**
 * 环境类
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class Context {
	private State state;

	public Context(State state) {
		this.state = state;
	}

	// 获取状态
	public State getState() {
		return state;
	}

	// 切换状态
	public void setState(State state) {
		this.state = state;
	}

	public void action() {
		state.action(this);
	}

}
