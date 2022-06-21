
/**
 * @author lwq
 * @date 2022/3/7 0007
 * @since
 */
public class People {
	private String name;

	public People() {
	}

	public People(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "I am a people, my name is " + name;
	}
}
