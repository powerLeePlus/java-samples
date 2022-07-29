package demo.lwq.spring.boot.starter.customizer;

/**
 * 减法服务的实现，支持负数结果
 * @author lwq
 * @date 2022/7/27 0027
 * @since
 */
public class MinusServiceSupportNegativeImpl implements MinusService {

	/**
	 * 减法运算，支持负数结果
	 * @author lwq
	 * @date 2022/7/27 0027 下午 6:55
	 * @param minuend
	 * @param subtraction
	 * @return int
	 */
	@Override
	public int minus(int minuend, int subtraction) throws MinusException {
		return minuend - subtraction;
	}
}
