package pattern.factory.factory_method;

import pattern.factory.Circle;
import pattern.factory.Shape;

/**
 * 工厂接口实现三：生产Circle
 * @author lwq
 * @date 2021/3/24 0024
 */
public class CircleFactory implements Factory{

	/** 3
	 * 具体工厂子类生产具体某一种产品
	 */
	@Override
	public Shape getShape() {
		return new Circle();
	}
}
