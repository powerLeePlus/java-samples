package pattern.bridge;

/**
 * 具体实现类 黑色
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class Black implements Color {
	@Override
	public void bepaint(String shape) {
		System.out.println("黑色的" + shape);
	}
}
