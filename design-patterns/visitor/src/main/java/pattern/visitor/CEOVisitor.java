package pattern.visitor;

/**
 * 抽象访问者 访问者 CEO
 * @author lwq
 * @date 2022/10/27 0027
 * @since
 */
public class CEOVisitor implements Visitor {
	@Override
	public void visit(Engineer engineer) {
		System.out.println("工程师：" + engineer.getName() + " KPI:" + engineer.getKpi());
	}

	@Override
	public void visit(Manager manager) {
		System.out.println("经理：" + manager.getName() + " KPI:" + manager.getKpi() + " 今年共完成项目：" + manager.getProductNum() + "个");
	}
}
