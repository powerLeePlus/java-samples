package pattern.visitor;

/**
 * 抽象访问者 访问者
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public interface Visitor {

	// 不同的员工（元素），访问的内容不同（属性）
	void visit(Engineer engineer);
	void visit(Manager manager);
}
