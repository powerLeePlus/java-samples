/**
 * @author lwq
 * @date 2022/3/2 0002
 * @since
 */
public class IntTest {
	public static void main(String[] args) {
		/**
		 * 对于对象引用类型：==比较的是对象的内存地址。
		 * 对于基本数据类型：==比较的是值。
		 * 如果整型字面量的值在-128到127之间，那么自动装箱时不会new新的Integer 对象，而是直接引用常量池中的Integer对象，超过范围 a1==b1的结果是false
		 */
		Integer a = new Integer(3);
		Integer b = 3; // 将3自动装箱成Integer类型
		int c = 3;
		System.out.println(a == b); // false 两个引用没有引用同一对象
		System.out.println(a == c); // true a自动拆箱成int类型再和c比较
		System.out.println(b == c); // true

		Integer a1 = 128;
		Integer b1 = 128;
		System.out.println(a1 == b1); // false

		Integer a2 = 127;
		Integer b2 = 127;
		System.out.println(a2 == b2); // true

	}
}
