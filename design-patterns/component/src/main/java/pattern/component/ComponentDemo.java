package pattern.component;

import java.util.List;

/**
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class ComponentDemo {

	public static void main(String[] args) {
		// 创建根节点对象
		Composite root = new Composite("root");

		// 创建两个组合节点对象
		Composite composite1 = new Composite("composite1");
		Composite composite2 = new Composite("composite2");

		// 将两个组合节点对象添加到根节点
		root.add(composite1);
		root.add(composite2);

		// 给第一个组合节点对象添加一个叶子节点和一个组合节点
		Leaf leaf1 = new Leaf("leaf1");
		Composite composite3 = new Composite("composite3");
		composite1.add(leaf1);
		composite1.add(composite3);

		// 给第二个组合节点对象添加两个叶子节点
		Leaf leaf2 = new Leaf("leaf2");
		Leaf leaf3 = new Leaf("leaf3");
		composite2.add(leaf2);
		composite2.add(leaf3);

		// 执行所有节点操作
		root.operation();
		Component child = root.getChild(1);
		System.out.println("root[1]:" + child);
		List<Component> children = root.getChildren();
		System.out.println("root:" + children);
		List<Component> children1 = child.getChild(0).getChildren();
		System.out.println("root[1][0]:" + children1);
	}
}
