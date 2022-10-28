package pattern.bridge;

/**
 * 扩展抽象类 圆形
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class Circle extends Shape {
	public Circle(Color color) {
		super(color);
	}

	@Override
	public void draw() {
		// 1.画圆
		String name = "圆形";
		// 2.上色
		color.bepaint(name);
	}
}
