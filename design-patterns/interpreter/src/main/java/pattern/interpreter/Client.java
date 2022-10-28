package pattern.interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 来自客户端的输入
 * @author lwq
 * @date 2022/10/28 0028
 * @since
 */
public class Client {

	// 获取键盘输入的表达式，如：a+b-c
	public static String getStrExpression() {
		System.out.println("请输入表达式(如a+b-c)：");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String s = bufferedReader.readLine();
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	// 获取键盘输入的变量值映射
	public static Map<String, Integer> getValue(String strExpression) {
		// 表达式变量对应的值
		Map<String, Integer> map = new HashMap<>();
		char[] chars = strExpression.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c != '+' && c != '-') {
				System.out.println("请输入[" + c + "]的值...");
				Scanner scanner = new Scanner(System.in);
				try {
					int nextInt = scanner.nextInt();
					map.put(String.valueOf(c), nextInt);
				} catch (Exception e) {
					System.out.println("[" + c + "]的值输入错误，必须是数字");
					i--;
					continue;
				}
			}
		}
		return map;
	}

	public static void main(String[] args) {
		boolean run = true;
		while (run) {
			// 接收用户输入表达式
			String strExpression = getStrExpression();
			// 接收用户输入表达式对应的变量值
			Map<String, Integer> map = getValue(strExpression);
			// 定义计算器
			Calculator calculator = new Calculator(strExpression);
			// 计算
			int calculate = calculator.calculate(map);
			System.out.println("表达式:" + strExpression + "，参数:" + map + "，计算结果:" + calculate);
			System.out.println("-------------------------------------");
			System.out.println("是否继续输入表达式计算：按1继续，按其他推出：");
			Scanner scanner = new Scanner(System.in);
			String s = scanner.nextLine();
			if (!s.equals("1")) {
				run = false;
			}
		}
	}
}
