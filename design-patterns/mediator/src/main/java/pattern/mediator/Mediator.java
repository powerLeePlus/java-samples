package pattern.mediator;

/**
 * 抽象中介者
 * @author lwq
 * @date 2022/10/26 0026
 * @since
 */
public abstract class Mediator {
	// 申明一个联络方法
	public abstract void contact(String message, Person person);
}
