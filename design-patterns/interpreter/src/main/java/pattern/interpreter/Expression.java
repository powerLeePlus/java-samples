package pattern.interpreter;

import java.util.Map;

/**
 * 表达式 抽象表达式
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public interface Expression {
	/**
	 * 解释表达式的抽象方法
	 * @param map 比如现有表达式：a+b；那么map中存放的就是{a=10,b=20}
	 * @return 返回解释后的值
	 */
	int interpreter(Map<String, Integer> map);
}
