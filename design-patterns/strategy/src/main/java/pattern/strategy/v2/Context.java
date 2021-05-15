package pattern.strategy.v2;

/**
 * 持有策略实例并执行策略的方法
 * @author lwq
 * @date 2021/3/23 0023
 */
public class Context {

	public static final String STRATEGY_TYPE_ADD = "ADD";
	public static final String STRATEGY_TYPE_MULTIPLY = "MULTIPLY";
	public static final String STRATEGY_TYPE_SUBTRACT = "SUBTRACT";

	/** 2
	 * 持有策略实例
	 */
	private Strategy strategy;

	public Context(String strategyType) {
		this.strategy = Strategy.instants.get(strategyType);
	}

	/** 3
	 * 调用具体某一个策略
	 */
	public int executeStrategy(int num1, int num2) {
		return strategy.doOperation(num1, num2);
	}
}
