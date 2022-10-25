package pattern.factory.abstract_factory.products1;

/**
 * 生产形状产品的工厂
 * @author lwq
 * @date 2021/3/24 0024
 */
public class ShapeFactory {

	/** 3
	 * 生产形状
	 */
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
}
