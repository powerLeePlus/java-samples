package pattern.factory.abstract_factory;

import pattern.factory.abstract_factory.products1.Shape;
import pattern.factory.abstract_factory.products2.Color;

/**
 * 抽象工厂。用于创建Shape和Color产品的工厂
 * @author lwq
 * @date 2021/3/24 0024
 */
public abstract class AbstractFactory {

	/** 2
	 * 抽象工厂定义生产产品的接口，需要各个产品工厂类实现具体的生产过程
	 */
	public abstract Shape getShape(String shape);
	public abstract Color getColor(String color);
}
