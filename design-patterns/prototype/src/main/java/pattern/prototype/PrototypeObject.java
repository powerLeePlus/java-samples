package pattern.prototype;

/**
 * @author lwq
 * @date 2022/10/25 0025
 * @since
 */
public class PrototypeObject implements Cloneable {
	private String name;

	public PrototypeObject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Object clone() {
		// 可以这里自定义拷贝方法
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"hashcode\":")
				.append(this.hashCode())
				.append(",");
		sb.append("\"name\":\"")
				.append(name).append('\"');
		sb.append('}');
		return sb.toString();
	}

	public static void main(String[] args) {
		PrototypeObject object = new PrototypeObject("张三");
		System.out.println(object);
		Object clone = object.clone();
		System.out.println(clone);
	}
}
