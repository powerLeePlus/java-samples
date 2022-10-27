package pattern.facade;

/**
 * 门面-对外提供访问
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Facade {

	private Light light1, light2;
	private Heater heater;
	private TV tv;

	public Facade() {
		light1 = new Light("客厅");
		light2 = new Light("卫生间");
		heater = new Heater();
		tv = new TV();
	}

	// 统一控制灯、热水器、电视的开关
	public void open() {
		light1.open();
		light2.open();
		heater.open();
		tv.open();
	}
}
