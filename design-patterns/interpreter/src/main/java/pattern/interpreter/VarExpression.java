package pattern.interpreter;

import java.util.Map;

/**
 * 变量解析器 终结符表达式
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class VarExpression implements Expression {
	// 公式中的变量
	private String key;

	public VarExpression(String key) {
		this.key = key;
	}

	// 通过Key获取对应的值
	@Override
	public int interpreter(Map<String, Integer> map) {
		return map.get(key);
	}
}
