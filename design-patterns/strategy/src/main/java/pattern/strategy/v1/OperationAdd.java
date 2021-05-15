package pattern.strategy.v1;

/**
 * 策略一
 * @author lwq
 * @date 2021/3/23 0023
 */
public class OperationAdd implements Strategy{
	@Override
	public int doOperation(int num1, int num2) {
		return num1 + num2;
	}
}
