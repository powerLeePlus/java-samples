package pattern.bridge;

/**
 * 扩展抽象类 长方形
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class Rectangle extends Shape {
	public Rectangle(Color color) {
		super(color);
	}

	@Override
	public void draw() {
		color.bepaint("长方形");
	}
}
