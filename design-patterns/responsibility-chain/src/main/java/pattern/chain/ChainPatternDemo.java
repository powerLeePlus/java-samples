package pattern.chain;

/**
 * @author lwq
 * @date 2021/3/22 0022
 */
public class ChainPatternDemo {

	/** 6
	 * 获取调用链调用链
	 */
	public static AbstractLogger getChainOfLoggers() {

		ErrorLogger errorLogger = new ErrorLogger(AbstractLogger.LEVEL_ERROR);
		FileLogger fileLogger = new FileLogger(AbstractLogger.LEVEL_DEBUG);
		ConsoleLogger consoleLogger = new ConsoleLogger(AbstractLogger.LEVEL_INFO);

		/**
		 * 要set下一链
		 */
		errorLogger.setNextLogger(consoleLogger);
		consoleLogger.setNextLogger(fileLogger);

		return errorLogger;
	}

	public static void main(String[] args) {
		AbstractLogger chainOfLoggers = getChainOfLoggers();

		/**
		 * 调用
		 */
		chainOfLoggers.logMessage(AbstractLogger.LEVEL_INFO, "this is a info level message");
		chainOfLoggers.logMessage(AbstractLogger.LEVEL_DEBUG, "this is a debug level message");
		chainOfLoggers.logMessage(AbstractLogger.LEVEL_ERROR, "this is an error level message");
	}
}
