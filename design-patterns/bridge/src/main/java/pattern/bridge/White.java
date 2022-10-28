package pattern.bridge;

/**
 * 具体实现类 白色
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class White implements Color {
	@Override
	public void bepaint(String shape) {
		System.out.println("白色的" + shape);
	}
}
