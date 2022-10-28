package pattern.interpreter;

import java.util.Map;

/**
 * 减法运算符
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class SubExpression extends SymbolExpression {
	public SubExpression(Expression leftExpression, Expression rightExpression) {
		super(leftExpression, rightExpression);
	}

	// 解释减法
	@Override
	public int interpreter(Map<String, Integer> map) {
		return leftExpression.interpreter(map) - rightExpression.interpreter(map);
	}
}
