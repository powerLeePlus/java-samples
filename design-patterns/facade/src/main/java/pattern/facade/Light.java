package pattern.facade;

/**
 * 子系统1-灯
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Light {

	private String name;

	public Light(String name) {
		this.name = name;
	}

	/**
	 * 开灯操作
	 */
	public void open() {
		System.out.println(name + "-灯已开");
	}
}
