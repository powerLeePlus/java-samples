package pattern.bridge;

/**
 * 客户端
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class Client {
	public static void main(String[] args) {
		Color white = new White();
		Color black = new Black();

		// 白色的圆
		Shape circle = new Circle(white);
		circle.draw();
		// 黑色的圆
		circle.setColor(black);
		circle.draw();

		// 白色的正方形
		Square square = new Square(white);
		square.draw();
		// 灰色的正方形
		square.setColor(new Gray());
		square.draw();
	}
}
