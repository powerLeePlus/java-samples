package pattern.factory.abstract_factory.products2;

/**
 *  产品一
 * @author lwq
 * @date 2021/3/24 0024
 */
public class Red implements Color {
	@Override
	public void fill() {
		System.out.println("Inside Red::fill() method.");
	}
}
