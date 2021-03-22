package pattern.chain;

/**
 *  责任链其三
 * @author lwq
 * @date 2021/3/22 0022
 */
public class ConsoleLogger extends AbstractLogger {

	public ConsoleLogger() {
	}

	public ConsoleLogger(int level) {
		this.level = level;
	}

	@Override
	void write(String message) {
		System.out.println("Console Logger：" + message);
	}
}
