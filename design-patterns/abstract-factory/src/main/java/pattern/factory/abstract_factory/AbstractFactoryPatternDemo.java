package pattern.factory.abstract_factory;

import pattern.factory.abstract_factory.products1.Shape;
import pattern.factory.abstract_factory.products2.Color;

/**
 * @author lwq
 * @date 2021/3/24 0024
 */
public class AbstractFactoryPatternDemo {

	public static void main(String[] args) {
		AbstractFactory factory1 = new Factory1();
		Shape circle = factory1.createShape("CIRCLE");
		Color red = factory1.createColor("RED");
		circle.draw();
		red.fill();

		AbstractFactory factory2 = new Factory2();
		Shape rectangle = factory2.createShape("RECTANGLE");
		Color green = factory2.createColor("GREEN");
		rectangle.draw();
		green.fill();
	}

}
