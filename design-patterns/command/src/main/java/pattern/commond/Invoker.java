package pattern.commond;

/**
 * 命令调用者
 * 电视遥控器
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Invoker {

	private Command command;

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	// 这是请求命令的入口
	public void action() {
		command.execute();
	}

	public static void main(String[] args) {
		// 电视
		Receiver receiver = new Receiver();

		// 打开电视命令
		Command openTV = new OpenCommand(receiver);
		// 关闭电视命令
		Command closeTV = new CloseCommand(receiver);

		// 遥控器
		Invoker invoker = new Invoker();

		// 请求打开电视操作
		invoker.setCommand(openTV);
		invoker.action();

		// 请求关闭电视操作
		invoker.setCommand(closeTV);
		invoker.action();
	}
}
