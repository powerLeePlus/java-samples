/**
 * @author lwq
 * @date 2022/6/2 0002
 * @since
 */
public class MemoryErrorTest {

	public static void main(String[] args) {
		testStackOverflowError();
	}

	/**
	 * 栈溢出 StackOverflowError
	 * 模拟：方法运行的时候栈的深度超过了JVM所容许的最大深度
	 * 举例：死递归
	 */
	public static void testStackOverflowError() {
		testStackOverflowError();

		// 结果
		/*Exception in thread "main" java.lang.StackOverflowError
		at MemoryErrorTest.testStackOverflowError(MemoryErrorTest.java:18)*/
	}
}
