package pattern.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 计算器 环境类
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class Calculator {
	private Expression expression;

	public Calculator(String strExpression) {
		char[] chars = strExpression.toCharArray();
		// 定义栈用于存储表达式，这里不考虑运算优先级，即从左到右按顺序计算。
		Stack<Expression> stack = new Stack<>();
		Expression left;
		Expression right;
		// 解析表达式
		for (int i = 0; i < chars.length; i++) {
			switch ((chars[i])) {
				case '+':
					// 获取左表达式
					left = stack.pop();
					// 定义右表达式
					right = new VarExpression(String.valueOf(chars[++i]));
					// 将其合并为一个新表达式，并放入栈中
					stack.push(new AddExpression(left, right));
					break;
				case '-':
					// 过程和加法一样
					left = stack.pop();
					right = new VarExpression(String.valueOf(chars[++i]));
					stack.push(new SubExpression(left, right));
					break;
				default:
					// 不是运算符，是数字
					stack.push(new VarExpression(String.valueOf(chars[i])));
					break;
			}
		}
		// 遍历完获取最终解析好的表达式
		this.expression = stack.pop();
	}

	/**
	 * 计算
	 * @param map 表达式对应的值
	 * @return 计算的结果
	 */
	public int calculate(Map<String, Integer> map) {
		return this.expression.interpreter(map);
	}

	public static void main(String[] args) {
		// 表达式
		String strExpression = "a+b-c+d";
		// 表达式变量对应的值
		Map<String, Integer> map = new HashMap<>();
		map.put("a", 2);
		map.put("b", 10);
		map.put("c", 8);
		map.put("d", 13);

		// 创建计算器
		Calculator calculator = new Calculator(strExpression);
		// 计算
		int calculate = calculator.calculate(map);
		System.out.println("表达式:" + strExpression + "，参数:" + map + "，计算结果:" + calculate);
	}
}
