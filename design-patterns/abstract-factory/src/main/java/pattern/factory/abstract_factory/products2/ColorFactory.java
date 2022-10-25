package pattern.factory.abstract_factory.products2;

/**
 * 生产颜色的工厂
 * @author lwq
 * @date 2021/3/24 0024
 */
public class ColorFactory {

	/** 3
	 * 生产颜色
	 */
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
