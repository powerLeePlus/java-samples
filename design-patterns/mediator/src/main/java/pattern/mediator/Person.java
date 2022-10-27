package pattern.mediator;

/**
 * 抽象同事类
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public abstract class Person {
	protected String name;
	protected Mediator mediator;

	public Person(String name, Mediator mediator) {
		this.name = name;
		this.mediator = mediator;
	}

	// 与中介者联系
	protected abstract void contact(String message);
	// 获取信息
	protected abstract void getMessage(String message);
}
