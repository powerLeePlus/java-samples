package pattern.state;

/**
 * 状态类-抽象
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public abstract class State {
	// 不同状态下的不同处理
	public abstract void action(Context context);
}
