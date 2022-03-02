/**
 * @author lwq
 * @date 2022/3/2 0002
 * @since
 */
public class StringTest {

	public static void main(String[] args) {
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
	}
}
