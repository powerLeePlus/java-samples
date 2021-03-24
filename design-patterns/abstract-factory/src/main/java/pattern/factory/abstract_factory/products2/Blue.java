package pattern.factory.abstract_factory.products2;

/**
 *  产品三
 * @author lwq
 * @date 2021/3/24 0024
 */
public class Blue implements Color {
	@Override
	public void fill() {
		System.out.println("Inside Blue::fill() method.");
	}
}
