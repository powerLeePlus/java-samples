package pattern.factory.simple_factory;

import pattern.factory.Circle;
import pattern.factory.Rectangle;
import pattern.factory.Shape;
import pattern.factory.Square;

/**
 * 一、简单工厂模式（不属于23种设计模式之一）
 * 生产产品Shape的工厂
 * @author lwq
 * @date 2021/3/23 0023
 */
public class ShapeFactory {

	/** 2
	 * 根据需要生产具体的产品
	 */
	public Shape getShape(String shapeType) {
		if (shapeType == null) {
			return null;
		}

		if (shapeType.equalsIgnoreCase("CIRCLE")) {
			return new Circle();
		} else if (shapeType.equalsIgnoreCase("SQUARE")) {
			return new Square();
		} else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
			return new Rectangle();
		}

		return null;
	}
}
