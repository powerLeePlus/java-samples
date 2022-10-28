package pattern.visitor;

/**
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class VisitorDemo {

	public static void main(String[] args) {
		// 工程师
		Engineer engineer1 = new Engineer("张工", 20);
		Engineer engineer2 = new Engineer("李工", 23);

		// 管理者
		Manager manager1 = new Manager("张总", 30);
		Manager manager2 = new Manager("李总", 34);

		// 所有员工集合
		EmployeeStructure structure = new EmployeeStructure();
		structure.add(engineer1);
		structure.add(engineer2);
		structure.add(manager1);
		structure.add(manager2);

		// CEO访问所有员工想看的内容和CTO不同
		System.out.println("CEO看的员工报告：");
		structure.report(new CEOVisitor());
		System.out.println("-----------------------------------------------");
		System.out.println("CTO看的员工报告：");
		// CTO访问所有员工想看的内容和CEO不同
		structure.report(new CTOVisitor());

	}
}
