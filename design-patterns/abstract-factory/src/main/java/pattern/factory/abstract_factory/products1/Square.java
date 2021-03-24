package pattern.factory.abstract_factory.products1;

/** 产品二
 * @author lwq
 * @date 2021/3/23 0023
 */
public class Square implements Shape {
	@Override
	public void draw() {
		System.out.println("Inside Square::draw() method.");
	}
}
