package pattern.factory.abstract_factory.products1;

/** 产品一
 * @author lwq
 * @date 2021/3/23 0023
 */
public class Rectangle implements Shape {
	@Override
	public void draw() {
		System.out.println("Inside Rectangle::draw() method.");
	}
}
