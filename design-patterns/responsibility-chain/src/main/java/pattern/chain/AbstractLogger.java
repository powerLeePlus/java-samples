package pattern.chain;

/**
 * 责任链接口
 * @author lwq
 * @date 2021/3/22 0022
 */
public abstract class AbstractLogger {

	public static final Integer LEVEL_INFO = 1;
	public static final Integer LEVEL_DEBUG = 2;
	public static final Integer LEVEL_ERROR = 3;

	protected int level;
	
	/** 1
	 * 需要引用自身实例（
	 * 责任链中的下一链
	 */
	protected AbstractLogger nextLogger;

	/** 2
	 * 每一个实例需要设置下一链
	 */
	public void setNextLogger(AbstractLogger nextLogger) {
		this.nextLogger = nextLogger;
	}

	public void logMessage(int level, String message) {
		/** 3
		 * 本链的处理
		 */
		if (this.level <= level) {
			write(message);
		}

		/** 4
		 * 调用下一链处理
		 */
		if (nextLogger != null) {
			nextLogger.logMessage(level, message);
		}

	}

	/** 5
	 * 每一链实现各自的处理
	 */
	abstract void write(String message);
}
