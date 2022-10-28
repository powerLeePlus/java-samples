package pattern.visitor;

/**
 * 具体元素 被访问者 管理者
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class Manager extends Employee {

	public Manager(String name, int kpi) {
		super(name, kpi);
	}

	@Override
	protected void accpet(Visitor visitor) {
		visitor.visit(this);
	}

	// 工程量-访问的差异点
	public int getProductNum() {
		return this.kpi * 10;
	}

}
