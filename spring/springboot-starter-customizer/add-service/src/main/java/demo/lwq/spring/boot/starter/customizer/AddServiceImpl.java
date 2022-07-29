package demo.lwq.spring.boot.starter.customizer;

/**
 * 加法服务实现
 * @author lwq
 * @date 2022/7/27 0027
 * @since
 */
public class AddServiceImpl implements AddService{
	@Override
	public int add(int a, int b) {
		return a + b;
	}
}
