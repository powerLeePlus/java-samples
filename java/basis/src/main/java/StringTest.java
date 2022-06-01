import java.util.ArrayList;
import java.util.List;

/**
 * @author lwq
 * @date 2022/3/2 0002
 * @since
 */
public class StringTest {

	public static void main(String[] args) {
		testString();
	}
	
	/**
	 * 字符串常量、字符串对象
	 */
	public static void testString() {
		/**
		 * 问：String可以被继承吗
		 * 答：String 类是 final 类，不可以被继承。
		 * 问：String str="i"与 String str=new String(“i”)一样吗？
		 * 答：不一样，因为内存的分配方式不一样。String str="i"的方式，java 虚拟机会将其分配到常量池中；而 String str=new String(“i”) 则会被分到堆内存
		 * 中。
		 * 问：String s = new String(“xyz”);创建了几个字符串对象
		 * 答：两个对象，一个是静态区的"xyz"，一个是用new创建在堆上的对象。
		 */
		String str1 = "hello"; //str1指向静态区
		String str2 = new String("hello"); //str2指向堆上的对象
		String str3 = "hello";
		String str4 = new String("hello");
		System.out.println(str1.equals(str2)); //true
		System.out.println(str2.equals(str4)); //true
		System.out.println(str1 == str3); //true
		System.out.println(str1 == str2); //false
		System.out.println(str2 == str4); //false
		System.out.println(str2 == "hello"); //false
		str2 = str1;
		System.out.println(str2 == "hello"); //true

		String s1 = "Hello";
		String s2 = "Hello";
		String s3 = "Hel" + "lo";
		String s4 = "Hel" + new String("lo");
		String s5 = new String("Hello");
		String s6 = s5.intern(); // 尝试将s5值"Hello"放入常量池中，并返回在常量池中地址
		String s7 = "H";
		String s8 = "ello";
		String s9 = s7 + s8;
		System.out.println(s1 == s2); // true
		System.out.println(s1 == s3); // true
		System.out.println(s1 == s4); // false
		System.out.println(s1 == s9); // false
		System.out.println(s4 == s5); // false
		System.out.println(s1 == s6); // true
	}

	/**
	 * 测试字符串常量池不在堆上，在永久代（JDK1.8后元空间）中
	 * 1、假设jvm启动参数为：-XX:PermSize＝2M -XX:MaxPermSize＝2M，JDK1.8：-XX:MaxMetaspaceSize=2M
	 * 2、然后运行如下代码
	 * 3、程序立刻会抛出：Exception in thread "main" java.lang.outOfMemoryError: PermGen space异
	 * 常。PermGen space正是方法区，足以说明常量池在方法区中。
	 *  在jdk8中，抛出：java.lang.OutOfMemoryError: Metaspace异常。同理说明运行时常量池是划分在Metaspace区域中。
	 */
	public static void testString2() {
		List<String> list = new ArrayList<>();
		int i = 0;
		while (true) {
			//通过intern方法向常量池中手动添加常量
			list.add(String.valueOf(i++).intern());
		}
	}
}
