package pattern.component;

/**
 * Leaf叶节点对象
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Leaf extends Component {
	public Leaf(String name) {
		super(name);
	}

	@Override
	public void operation() {
		System.out.println("叶节点:"+name+"的操作");
	}
}
