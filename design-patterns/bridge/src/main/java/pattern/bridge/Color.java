package pattern.bridge;

/**
 * 实现类 颜色
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public interface Color {

	// 由形状去调用，上色
	void bepaint(String shape);
}
