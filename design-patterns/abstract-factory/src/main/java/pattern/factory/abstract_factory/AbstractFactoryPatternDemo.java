package pattern.factory.abstract_factory;

import pattern.factory.abstract_factory.products1.Shape;
import pattern.factory.abstract_factory.products2.Color;

/**
 * @author lwq
 * @date 2021/3/24 0024
 */
public class AbstractFactoryPatternDemo {

	public static void main(String[] args) {
		createProducts1();
		createProducts2();
	}

	public static void createProducts1() {
		AbstractFactory shapeFactory = FactoryProducer.getFactory("SHAPE");

		Shape rectangle = shapeFactory.getShape("RECTANGLE");
		rectangle.draw();

		Shape square = shapeFactory.getShape("SQUARE");
		square.draw();

		Shape circle = shapeFactory.getShape("CIRCLE");
		circle.draw();

	}

	public static void createProducts2() {
		AbstractFactory colorFactory = FactoryProducer.getFactory("COLOR");

		Color red = colorFactory.getColor("RED");
		red.fill();

		Color green = colorFactory.getColor("GREEN");
		green.fill();

		Color blue = colorFactory.getColor("blue");
		blue.fill();
	}
}
