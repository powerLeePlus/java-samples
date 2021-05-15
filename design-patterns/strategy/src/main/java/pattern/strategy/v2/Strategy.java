package pattern.strategy.v2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

/**
 * 策略接口
 * @author lwq
 * @date 2021/3/23 0023
 */
public interface Strategy {

	Map<String, Strategy> instants = new ConcurrentHashMap<>();

	/** 1
	 * 公共的策略接口，不同的策略实现
	 */
	public int doOperation(int num1, int num2);

	String getStrategyType();

	@PostConstruct
	default void init() {
		instants.put(getStrategyType(), this);
	}
}
