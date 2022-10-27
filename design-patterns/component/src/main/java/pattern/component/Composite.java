package pattern.component;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite组合节点对象
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Composite extends Component {
	private List<Component> components = new ArrayList<>();

	public Composite(String name) {
		super(name);
	}

	@Override
	public void operation() {
		System.out.println("组合节点:"+name+"的操作");
		// 调用所有子节点的操作
		components.forEach(Component::operation);
	}

	@Override
	public void add(Component component) {
		components.add(component);
	}

	@Override
	public void remove(Component component) {
		components.remove(component);
	}

	@Override
	public Component getChild(int i) {
		return components.get(i);
	}

	@Override
	public List<Component> getChildren() {
		return components;
	}
}
