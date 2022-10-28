package pattern.visitor;

/**
 * 抽象元素 被访问者 员工
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public abstract class Employee {
	protected String name;
	protected int kpi;

	public Employee(String name, int kpi) {
		this.name = name;
		this.kpi = kpi;
	}

	// 不同的具体元素，返回不同的内容
	protected abstract void accpet(Visitor visitor);

	public String getName() {
		return name;
	}

	public int getKpi() {
		return kpi;
	}

}
