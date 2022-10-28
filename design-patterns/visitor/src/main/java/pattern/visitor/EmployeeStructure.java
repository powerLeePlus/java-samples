package pattern.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 结构对象 维护被访问者集合 员工集合
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class EmployeeStructure {
	private List<Employee> employees = new ArrayList<>();

	public void add(Employee employee) {
		employees.add(employee);
	}

	// 遍历迭代访问所有元素
	public void report(Visitor visitor) {
		employees.forEach(e -> e.accpet(visitor));
	}
}
