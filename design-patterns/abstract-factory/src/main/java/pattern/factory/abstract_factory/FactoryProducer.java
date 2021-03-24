package pattern.factory.abstract_factory;

/**
 * 生产工厂的
 * @author lwq
 * @date 2021/3/24 0024
 */
public class FactoryProducer {

	/** 4
	 * 根据选择生产不同的产品工厂
	 */
	public static AbstractFactory getFactory(String choice) {
		if(choice.equalsIgnoreCase("SHAPE")){
			return new ShapeFactory();
		} else if(choice.equalsIgnoreCase("COLOR")){
			return new ColorFactory();
		}
		return null;
	}
}
