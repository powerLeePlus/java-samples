package pattern.mediator;

/**
 * 具体同事类-承租人
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Tenant extends Person {
	public Tenant(String name, Mediator mediator) {
		super(name, mediator);
	}

	@Override
	protected void contact(String message) {
		mediator.contact(message, this);
	}

	@Override
	protected void getMessage(String message) {
		System.out.println("承租人:" + name + "，收到消息:" + message);
	}
}
