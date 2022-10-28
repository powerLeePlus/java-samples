package pattern.visitor;

/**
 * 具体元素 被访问者 工程师
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Engineer extends Employee {

	public Engineer(String name, int kpi) {
		super(name, kpi);
	}

	@Override
	protected void accpet(Visitor visitor) {
		visitor.visit(this);
	}

	// 代码量-访问的差异点
	public int getCodeLineTotal() {
		return this.kpi * 10000;
	}

}
