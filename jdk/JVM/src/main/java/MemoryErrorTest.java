import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * 内存错误案例演示
 * @author lwq
 * @date 2022/6/2 0002
 * @since
 */
public class MemoryErrorTest {

	public static void main(String[] args) {
		// testStackOverflowError();
		// testOOMHeap();
		// testOOMStringIntern();
		testOOMPerm();
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
	
	/**
	 * OutOfMemoryError:java heap space,
	 * 模拟：将堆内存设置的小些，然后创建大数组
	 * java -verbose:gc -Xmn10M -Xms20M -Xmx20M -XX:+PrintGC MemoryErrorTest
	 */
	public static void testOOMHeap() {
		List<byte[]> buffer = new ArrayList<>();
		buffer.add(new byte[10*1024*1024]);

		/* 结果
		[GC (Allocation Failure)  1987K->752K(19456K), 0.0011267 secs]
		[GC (Allocation Failure)  752K->720K(19456K), 0.0008298 secs]
		[Full GC (Allocation Failure)  720K->634K(19456K), 0.0060419 secs]
		[GC (Allocation Failure)  634K->634K(19456K), 0.0004575 secs]
		[Full GC (Allocation Failure)  634K->615K(19456K), 0.0069755 secs]
		Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
			at MemoryErrorTest.testOutOfMemoryErrorHeap(MemoryErrorTest.java:36)
			at MemoryErrorTest.main(MemoryErrorTest.java:13)
		*/

		/* 增加参数-XX:+PrintGCDetails，打印详细GC日志，结果如下：
		[GC (Allocation Failure) [PSYoungGen: 1987K->744K(9216K)] 1987K->752K(19456K), 0.0312052 secs] [Times: user=0.00 sys=0.00, real=0.03 secs]
		[GC (Allocation Failure) [PSYoungGen: 744K->696K(9216K)] 752K->704K(19456K), 0.0009500 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
		[Full GC (Allocation Failure) [PSYoungGen: 696K->0K(9216K)] [ParOldGen: 8K->634K(10240K)] 704K->634K(19456K), [Metaspace: 3274K->3274K(1056768K)], 0.0085761 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
		[GC (Allocation Failure) [PSYoungGen: 0K->0K(9216K)] 634K->634K(19456K), 0.0003849 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
		[Full GC (Allocation Failure) [PSYoungGen: 0K->0K(9216K)] [ParOldGen: 634K->615K(10240K)] 634K->615K(19456K), [Metaspace: 3274K->3274K(1056768K)], 0.0071522 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
		Heap
		 PSYoungGen      total 9216K, used 246K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
		  eden space 8192K, 3% used [0x00000000ff600000,0x00000000ff63d890,0x00000000ffe00000)
		  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
		  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
		 ParOldGen       total 10240K, used 615K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
		  object space 10240K, 6% used [0x00000000fec00000,0x00000000fec99f88,0x00000000ff600000)
		 Metaspace       used 3305K, capacity 4496K, committed 4864K, reserved 1056768K
		  class space    used 362K, capacity 388K, committed 512K, reserved 1048576K
		Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
			at MemoryErrorTest.testOutOfMemoryErrorHeap(MemoryErrorTest.java:36)
			at MemoryErrorTest.main(MemoryErrorTest.java:13)
		*/

		/**
		 * GC日志分析
		 * 因为配置了-Xmn10M，所以新生代内存是10m=10240k，默认Eden和From-Survive和To-Survive内存比例是：8:1:1，因为只有一块Survice区可使用，所以，新生代总内存是9m=9216k；
		 * 因为配置了-Xms20M -Xmx20M，所以老年代内存是10m=10240k；
		 * 所以新生代和老年代总可用内存是：19m=19456k
		 * 1、创建List，存放在Eden区
		 * 2、向List add字节数组，由于字节数组需要10*1024*1024=10m空间，Eden只有不到8m，所以进行Minor-gc，gc后空间也不足10m，（有8k进入了老年代）；
		 * 3、需要直接将该字节数组放入老年代，但是老年代可用空间不足10m，所以触发Full-gc（2次），gc后也凑不够10m空间，所以抛出了OOM。
		 *
		 */
		/**
		 * 从侧面验证了一个结论：对象大于新生代剩余内存的时候，将直接放入老年代，
		 * 当老年代剩余内存还是无法放下的时候，触发垃圾收集，收集后还是不能放下就会抛出内存溢出异常
		 * 了。
		 */

		/**
		 * 将堆内存大小改为21m，就不会出现OOM了。
		 * java -verbose:gc -Xmn10M -Xms21M -Xmx21M -XX:+PrintGC MemoryErrorTest
		 */

		/* 结果
		 Heap
		 PSYoungGen      total 9216K, used 2151K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
		  eden space 8192K, 26% used [0x00000000ff600000,0x00000000ff819f28,0x00000000ffe00000)
		  from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
		  to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
		 ParOldGen       total 12288K, used 10240K [0x00000000fea00000, 0x00000000ff600000, 0x00000000ff600000)
		  object space 12288K, 83% used [0x00000000fea00000,0x00000000ff400010,0x00000000ff600000)
		 Metaspace       used 3280K, capacity 4496K, committed 4864K, reserved 1056768K
		  class space    used 359K, capacity 388K, committed 512K, reserved 1048576K
		 */

		/**
		 * 分析，可以看到直接将字节数组放在了老年代
		 * 疑问：为啥老年代总大小是12288k=12m呢，21-10=11应该是11啊？难道是JVM自动按2的整数倍设置的吗？
		 *      将21改为22，老年代仍是12m；改为23却是和改为24一样都是14m。神奇！！！
		 */
	}

	/**
	 * jdk1.8以下：永久代溢出(OutOfMemoryError: PermGen space)
	 * Hotspot jvm通过永久代实现了Java虚拟机规范中的方法区，永久代溢出可能是方法区中保存的class对象没有被及时
	 * 回收掉或者class信息占用的内存超过了配置。
	 *
	 * jdk1.8：元空间溢出(java.lang.OutOfMemoryError: Metaspace)
	 *
	 * JVM参数：-Xmx50m -XX:-UseCompressedOops -XX:+PrintGCDetails
	 *  jdk1.7：-XX:MaxPermSize=4m
	 *  jdk1.8：-XX:MaxMetaspaceSize=4m
	 */
	public static void testOOMPerm() {
		URL url = null;
		List<ClassLoader> classLoaderList = new ArrayList<ClassLoader>();
		try {
			url = new File("/tmp").toURI().toURL();
			URL[] urls = {url};
			while (true){
				ClassLoader loader = new URLClassLoader(urls);
				classLoaderList.add(loader);
				loader.loadClass("HelloGC");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* 结果
		[GC (Metadata GC Threshold) [PSYoungGen: 1559K->792K(14848K)] 1559K->792K(49152K), 0.0025444 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
		[Full GC (Metadata GC Threshold) [PSYoungGen: 792K->0K(14848K)] [ParOldGen: 0K->692K(22528K)] 792K->692K(37376K), [Metaspace: 2704K->2704K(8192K)], 0.0055080 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
		[GC (Last ditch collection) [PSYoungGen: 0K->0K(14848K)] 692K->692K(37376K), 0.0003796 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
		[Full GC (Last ditch collection) [PSYoungGen: 0K->0K(14848K)] [ParOldGen: 692K->674K(34304K)] 692K->674K(49152K), [Metaspace: 2704K->2704K(8192K)], 0.0063723 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
		FATAL ERROR in native method: processing of -javaagent failed
		java.lang.OutOfMemoryError: Metaspace
			at java.lang.ClassLoader.defineClass1(Native Method)
			at java.lang.ClassLoader.defineClass(ClassLoader.java:763)
			at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
			at java.net.URLClassLoader.defineClass(URLClassLoader.java:467)
			at java.net.URLClassLoader.access$100(URLClassLoader.java:73)
			at java.net.URLClassLoader$1.run(URLClassLoader.java:368)
			at java.net.URLClassLoader$1.run(URLClassLoader.java:362)
			at java.security.AccessController.doPrivileged(Native Method)
			at java.net.URLClassLoader.findClass(URLClassLoader.java:361)
			at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
			at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
			at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
			at sun.instrument.InstrumentationImpl.loadClassAndStartAgent(InstrumentationImpl.java:304)
			at sun.instrument.InstrumentationImpl.loadClassAndCallPremain(InstrumentationImpl.java:401)
		Exception in thread "main"
		*/
	}

	/**
	 * 通过String.intern()将字符串放入字符串常量池，模拟OutOfMemoryError: Java heap space
	 *
	 * jdk1.7后将字符串常量池和类静态变量转移到了java堆。所以用String.intern()无法模拟出永久代（元空间）溢出
	 *
	 * JVM参数：-Xms10M -Xmx10M
	 */
	static String  base = "string";
	public static void testOOMStringIntern() {
		// List<String> list = new ArrayList<>();
		// while (true) {
		// 	list.add(UUID.randomUUID().toString().intern());
		// }
		List<String> list = new ArrayList<String>();
		for (int i=0;i< Integer.MAX_VALUE;i++){
			String str = base + base;
			base = str;
			list.add(str.intern());
		}

		/* 结果
		Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
		at java.util.Arrays.copyOf(Arrays.java:3332)
		at java.lang.AbstractStringBuilder.expandCapacity(AbstractStringBuilder.java:137)
		at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:121)
		at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:421)
		at java.lang.StringBuilder.append(StringBuilder.java:136)
		at MemoryErrorTest.testOOMStringIntern(MemoryErrorTest.java:142)
		at MemoryErrorTest.main(MemoryErrorTest.java:15)
		*/
	}

}
