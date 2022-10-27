package pattern.commond;

/**
 * 命令接收者
 * 电视
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Receiver {

	// 执行命令
	public void openTV() {
		System.out.println("电视已打开");
	}

	// 执行命令
	public void closeTV() {
		System.out.println("电视已关闭");
	}
}
