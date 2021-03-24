package pattern.factory.abstract_factory;

import pattern.factory.abstract_factory.products1.Circle;
import pattern.factory.abstract_factory.products1.Rectangle;
import pattern.factory.abstract_factory.products1.Shape;
import pattern.factory.abstract_factory.products1.Square;
import pattern.factory.abstract_factory.products2.Color;

/**
 * 生产Shape产品的工厂
 * @author lwq
 * @date 2021/3/24 0024
 */
public class ShapeFactory extends AbstractFactory {

	/** 3
	 * 具体工厂实现具体的生产自己产品的guoc
	 */
	@Override
	public Shape getShape(String shapeType) {
		if(shapeType == null){
			return null;
		}
		if(shapeType.equalsIgnoreCase("CIRCLE")){
			return new Circle();
		} else if(shapeType.equalsIgnoreCase("RECTANGLE")){
			return new Rectangle();
		} else if(shapeType.equalsIgnoreCase("SQUARE")){
			return new Square();
		}
		return null;
	}

	@Override
	public Color getColor(String color) {
		return null;
	}
}
