package pattern.strategy;

/**
 * 策略三
 * @author lwq
 * @date 2021/3/23 0023
 */
public class OperationMultiply implements Strategy{
	@Override
	public int doOperation(int num1, int num2) {
		return num1 * num2;
	}
}
