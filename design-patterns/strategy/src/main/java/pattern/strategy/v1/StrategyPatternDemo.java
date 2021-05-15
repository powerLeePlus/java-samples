package pattern.strategy.v1;

/**
 * @author lwq
 * @date 2021/3/23 0023
 */
public class StrategyPatternDemo {

	public static void main(String[] args) {
		/** 4
		 * 实际使用时选择不同策略
		 */
		Context context1 = new Context(new OperationAdd());
		int i1 = context1.executeStrategy(10, 5);
		System.out.println("10 + 5 = " + i1);

		Context context2 = new Context(new OperationSubtract());
		int i2 = context2.executeStrategy(10, 5);
		System.out.println("10 - 5 = " + i2);

		Context context3 = new Context(new OperationMultiply());
		int i3 = context3.executeStrategy(10, 5);
		System.out.println("10 * 5 = " + i3);
	}
}
