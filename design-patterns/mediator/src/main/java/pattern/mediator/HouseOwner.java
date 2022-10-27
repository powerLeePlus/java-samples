package pattern.mediator;

/**
 * 具体同事类-房主
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class HouseOwner extends Person {
	public HouseOwner(String name, Mediator mediator) {
		super(name, mediator);
	}

	@Override
	protected void contact(String message) {
		mediator.contact(message, this);
	}

	@Override
	protected void getMessage(String message) {
		System.out.println("房主:" + name + "，获取到信息:" + message);
	}
}
