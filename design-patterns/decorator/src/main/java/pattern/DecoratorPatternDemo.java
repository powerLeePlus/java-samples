package pattern;

import pattern.decorator.impl.RedSharpDecorator;
import pattern.impl.Circle;
import pattern.impl.Rectangle;

/**
 * @author lwq
 * @date 2020/5/9 0009
 */
public class DecoratorPatternDemo {
	public static void main(String[] args) {
		Sharp circle = new Circle();
		RedSharpDecorator redCircle = new RedSharpDecorator(new Circle());
		RedSharpDecorator redRectangle = new RedSharpDecorator(new Rectangle());
		//Shape redCircle = new RedShapeDecorator(new Circle());
		//Shape redRectangle = new RedShapeDecorator(new Rectangle());
		System.out.println("Circle with normal border:");
		circle.draw();

		System.out.println("\nCircle of red border:");
		redCircle.draw();

		System.out.println("\nRectangle of red border");
		redRectangle.draw();
	}
}
