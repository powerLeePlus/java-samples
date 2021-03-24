package pattern.factory.abstract_factory;

import pattern.factory.abstract_factory.products1.Shape;
import pattern.factory.abstract_factory.products2.Blue;
import pattern.factory.abstract_factory.products2.Color;
import pattern.factory.abstract_factory.products2.Green;
import pattern.factory.abstract_factory.products2.Red;

/**
 * 生产Color产品的工厂
 * @author lwq
 * @date 2021/3/24 0024
 */
public class ColorFactory extends AbstractFactory {

	@Override
	public Shape getShape(String shapeType) {
		return null;
	}

	/** 3
	 * 具体工厂实现具体的生产自己产品的guoc
	 */
	@Override
	public Color getColor(String color) {
		if(color == null){
			return null;
		}
		if(color.equalsIgnoreCase("RED")){
			return new Red();
		} else if(color.equalsIgnoreCase("GREEN")){
			return new Green();
		} else if(color.equalsIgnoreCase("BLUE")){
			return new Blue();
		}
		return null;
	}
}
