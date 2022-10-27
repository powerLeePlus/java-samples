package pattern.component;

import java.util.List;

/**
 * Component抽象组件-对外提供统一方法
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public abstract class Component {
	protected String name;

	public Component(String name) {
		this.name = name;
	}

	// 单个对象和组合对象都支持的操作
	public abstract void operation();

	// 单个对象不支持，组合对象支持的操作
	public void add(Component component) {
		throw new UnsupportedOperationException();
	}

	public void remove(Component component) {
		throw new UnsupportedOperationException();
	}

	public Component getChild(int i) {
		throw new UnsupportedOperationException();
	}

	public List<Component> getChildren() {
		return null;
	}

}
