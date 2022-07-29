package demo.lwq.spring.boot.starter.customizer;

/**
 * 减法服务
 * @author lwq
 * @date 2022/7/27 0027
 * @since
 */
public interface MinusService {
	
	/**
	 * 普通减法
	 * @author lwq
	 * @date 2022/7/27 0027 下午 6:45
	 * @param minuend
	 * @param subtraction
	 * @return int
	 * @throws MinusException 如果已经配置了不支持负数的减法服务，那么被减数如果小于减数就抛出异常：
	 */
	int minus(int minuend, int subtraction) throws MinusException;
}
