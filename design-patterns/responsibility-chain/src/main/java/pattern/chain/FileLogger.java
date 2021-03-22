package pattern.chain;

/**
 *  责任链其二
 * @author lwq
 * @date 2021/3/22 0022
 */
public class FileLogger extends AbstractLogger {

	public FileLogger(int level) {
		this.level = level;
	}

	@Override
	void write(String message) {
		System.out.println("File Logger：" + message);
	}
}
