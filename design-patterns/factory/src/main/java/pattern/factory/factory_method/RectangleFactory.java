package pattern.factory.factory_method;

import pattern.factory.Rectangle;
import pattern.factory.Shape;

/**
 * 工厂接口实现一：生产Rectangle
 * @author lwq
 * @date 2021/3/24 0024
 */
public class RectangleFactory implements Factory{

	/** 3
	 * 具体工厂子类生产具体某一种产品
	 */
	@Override
	public Shape getShape() {
		return new Rectangle();
	}
}
