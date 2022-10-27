package pattern.memento;

/**
 * 原发器：需要保存历史版本数据的对象
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Originator {
	// 当前的数据
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// 生产备忘录
	public Memento createMemento() {
		return new Memento(state);
	}

	// 从备忘录中恢复数据
	public void restoreStateFromMemento(Memento memento) {
		this.state = memento.getState();
	}

	/**
	 * 备忘录：保存历史版本数据
	 * @author lwq
	 * @date 2022/10/27 0027
	 * @since
	 */
	protected class Memento {
		// 备忘录中保存的数据
		private String state;

		Memento(String state) {
			this.state = state;
		}

		String getState() {
			return state;
		}
	}
}
