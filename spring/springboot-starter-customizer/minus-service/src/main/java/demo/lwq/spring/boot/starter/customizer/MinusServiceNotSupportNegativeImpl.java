package demo.lwq.spring.boot.starter.customizer;

/**
 * 减法服务的实现，不支持负数
 * @author lwq
 * @date 2022/7/27 0027
 * @since
 */
public class MinusServiceNotSupportNegativeImpl implements MinusService {

	/**
	 * 减法运算，不支持负数结果，如果被减数小月减数，就抛出MinusException异常
	 * @author lwq
	 * @date 2022/7/27 0027 下午 6:55
	 * @param minuend
	 * @param subtraction
	 * @return int
	 */
	@Override
	public int minus(int minuend, int subtraction) throws MinusException {
		if (subtraction > minuend) {
			throw new MinusException("not support negative!");
		}
		return minuend - subtraction;
	}
}
