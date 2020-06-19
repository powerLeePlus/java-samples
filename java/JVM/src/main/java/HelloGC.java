import java.util.LinkedList;
import java.util.List;

/**
 * @author lwq
 * @date 2020/6/19 0019
 */
public class HelloGC {
	public static void main(String[] args) {
		System.out.println("HelloGC!");
		List list = new LinkedList();
		for(;;) {
			byte[] b = new byte[1024*1024];
			list.add(b);
		}

		/* run result:
		 * HelloGC!
		 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
		 * at lwq.demo.jvm.HelloGC.main(HelloGC.java:15)
		 */

		/* add VM option to run:
		 * - java -XX:+PrintCommandLineFlags HelloGC
		 * - java -Xmn10M -Xms40M -Xmx60M -XX:+PrintCommandLineFlags -XX:+PrintGC  HelloGC PrintGCDetails
		 *   PrintGCTimeStamps PrintGCCauses
		 */

		/* java 直接执行 class文件：以下示例：Hello.java
		 * 1.如果没有package，则直接在Hello.java所在目录执行：javac Hello.java 再执行 java Hello
		 * 2.如果有package（即文件头部有：package xxx ， 举例：package lwq.demo.jvm），
		 *   则有两种方式编译
		 *     2.1：在Hello.java所在目录执行 javac Hello.java，然后手动建出：lwq/demo/jvm目录，把编译好的Hello.class放到该目录下
		 *     2.2：在任意目录执行 javac -d . Hello.java，则会自动建出包路径：lwq/demo/jvm，编译好的Hello.class自动在该目录下
		 *   执行class文件：在lwq所在父级目录执行：java lwq.demo.jvm.Hello （注意一定要带上完整包名）
		 *
		 */
	}
}
