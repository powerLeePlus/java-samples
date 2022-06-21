import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * @author lwq
 * @date 2022/6/7 0007
 * @since
 */
public class ReferenceTest {

	/**
	 * java有4种引用类型：强、软、弱、虚
	 * 强引用：不会被GC回收，JVM宁愿抛出OOM。如:A a = new A();
	 * 软引用：内存不够会被回收；内存充足不会被回收。可以用来实现内存敏感的高速缓存
	 * 弱引用：不管内存够不够，只要被垃圾收集器扫描到就会被回收。
	 * 虚引用：“形同虚设”，作用是为了用来跟踪对象被垃圾回收器回收的活动；必须和引用队列（ReferenceQueue）联合使用
	 *
	 * 软引用、弱引用和虚引用均为抽象类 java.lang.ref.Reference 的子类，而与引用队列和GC相关的操作
	 * 大多在抽象类Reference中实现。
	 *
	 * 那么问题来了，若一个对象的引用类型有多个，那到底如何判断它的可达性呢？其实规则如下：
	 *  1、单条引用链以最弱的一个引用类型决定
	 *  2、多条引用链以最强的那条引用链决定。
	 */

	public static void main(String[] args) {
		testSoftReference();
	}
	
	/**
	 * 利用软引用实现内存敏感的高速缓存
	 */
	public static void testSoftReference() {
		ReferenceQueue q = new ReferenceQueue();

		// 获取数据并缓存
		Object obj = new Object();
		SoftReference sr = new SoftReference(obj, q);

		// 下次使用时
		Object obj2 = (Object)sr.get();
		if (obj2 == null) {
			// 当软引用被回收后才重新获取
			obj2 = new Object();
		}

		// 清理被回收后剩下来的软引用对象
		Reference ref = null;
		while ((ref = q.poll()) != null) {
			// 清理工作
			ref = null;
		}
	}
}
