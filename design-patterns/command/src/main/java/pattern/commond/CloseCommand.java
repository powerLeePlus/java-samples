package pattern.commond;

/**
 * 具体命令
 * 关闭电视
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class CloseCommand implements Command {

	// 命令要持有接收者的引用
	private Receiver receiver;

	public CloseCommand(Receiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void execute() {
		// 调用接收者方法执行命令
		receiver.closeTV();
	}
}
