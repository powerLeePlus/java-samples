package pattern.strategy.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lwq
 * @date 2021/3/23 0023
 */
@SpringBootApplication
public class StrategyPatternDemo {

	public static void main(String[] args) {
		SpringApplication.run(StrategyPatternDemo.class, args);

		/** 4
		 * 实际使用时选择不同策略
		 */
		Context context1 = new Context(Context.STRATEGY_TYPE_ADD);
		int i1 = context1.executeStrategy(10, 5);
		System.out.println("10 + 5 = " + i1);

		Context context2 = new Context(Context.STRATEGY_TYPE_SUBTRACT);
		int i2 = context2.executeStrategy(10, 5);
		System.out.println("10 - 5 = " + i2);

		Context context3 = new Context(Context.STRATEGY_TYPE_MULTIPLY);
		int i3 = context3.executeStrategy(10, 5);
		System.out.println("10 * 5 = " + i3);
	}
}
