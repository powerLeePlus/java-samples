package pattern.bridge;

/**
 * 具体实现类 灰色
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class Gray implements Color {
	@Override
	public void bepaint(String shape) {
		System.out.println("灰色的" + shape);
	}
}
