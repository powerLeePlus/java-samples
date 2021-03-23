package pattern.strategy;

/**
 * 持有策略实例并执行策略的方法
 * @author lwq
 * @date 2021/3/23 0023
 */
public class Context {

	/** 2
	 * 持有策略实例
	 */
	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	/** 3
	 * 调用具体某一个策略
	 */
	public int executeStrategy(int num1, int num2) {
		return strategy.doOperation(num1, num2);
	}
}
