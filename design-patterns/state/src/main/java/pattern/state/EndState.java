package pattern.state;

/**
 * 状态-具体 结束状态
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class EndState extends State {
	@Override
	public void action(Context context) {
		System.out.println("这就结束了？");
	}

	@Override
	public String toString() {
		return "state:end";
	}
}
