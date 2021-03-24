package pattern.factory.factory_method;

import pattern.factory.Shape;

/**
 * 二、工厂模式(工厂方法)
 * 生产产品Shape的工厂
 * @author lwq
 * @date 2021/3/23 0023
 */
public interface Factory {

	/** 2
	 * 定义工厂接口，具体产品的生产放在具体工厂子类里
	 */
	public abstract Shape getShape();
}
