package pattern.interpreter;

import java.util.Map;

/**
 * 运算符解析器 非终结符表达式
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class SymbolExpression implements Expression {

	/**
	 * 假设现有表达式：a + b - c 需要解析。
	 * 分析：
	 *  1. 一个运算符连接的是它左右两个数字。
	 *  2. 如上表达式 + 号连接的是 a 和 b , - 号连接的是 a+b 和 c。
	 *  3.经此分析我们将运算符连接的左右都看成是一个表达式，也就是Expression，
	 */
	// 左表达式
	protected Expression leftExpression;
	// 右表达式
	protected Expression rightExpression;

	public SymbolExpression(Expression leftExpression, Expression rightExpression) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}

	// 不同种类的运算符由不同的运算符子类进行解析，所以该类不实现
	@Override
	public int interpreter(Map<String, Integer> map) {
		return 0;
	}
}
