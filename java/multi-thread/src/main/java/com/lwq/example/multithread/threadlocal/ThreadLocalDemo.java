package com.lwq.example.multithread.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lwq
 * @date 2020/7/13 0013
 */
public class ThreadLocalDemo {

	/**
	 * 当需要存储线程私有变量的时候，可以考虑使用ThreadLocal来实现
	 * 当需要实现线程安全的变量时，可以考虑使用ThreadLocal来实现
	 * 当需要减少线程资源竞争的时候，可以考虑使用ThreadLocal来实现
	 *
	 * 注意Thread实例和ThreadLocal实例的生存周期，因为他们直接关联着存储数据的生命周期
	 * 如果频繁的在线程中new ThreadLocal对象，在使用结束时，最好调用ThreadLocal.remove来释放其value的引用，避免在ThreadLocal被回收时value无法被访问却又占用着内存
	 * 内存泄露举例：
	 ***使用线程池提供线程，则实际每次任务执行完归还线程，但是线程实际并未退出，则线程属性ThreadLocalMap不会被回收，
	 ***作为key的ThreadLocal和作为value的变量还在被引用，所以也不会被回收，但是实际上任务执行完该变量已经不再使用了，
	 ***由此造成了内存泄露，最终可能触发OOM
	 *
	 * 链接：https://www.jianshu.com/p/1a5d288bdaee
	 *
	 * 阿里代码规约：
	 ** 【参考】ThreadLocal 对象使用 static 修饰，ThreadLocal 无法解决共享对象的更新问题。
	 ** 说明：这个变量是针对一个线程内所有操作共享的，所以设置为静态变量，所有此类实例共享此静态变量，
	 ** 也就是说在类第一次被使用时装载，只分配一块存储空间，所有此类的对象(只要是这个线程内定义的)都可
	 ** 以操控这个变量。
	 *
	 * */

	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static ThreadLocal<SimpleDateFormat> sdfThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		}
	};

	ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1000));

	/**
	 * 线程不安全，见 {@link SimpleDateFormatTest}
	 */
	public void test0() {
		for (int i = 0; i < 1000; i++) {
			poolExecutor.execute(new Runnable() {
				@Override
				public void run() {

					String dateString = simpleDateFormat.format(new Date());
					try {
						Date parseDate = simpleDateFormat.parse(dateString);
						String dateString2 = simpleDateFormat.format(parseDate);
						System.out.println(dateString.equals(dateString2));
					} catch (ParseException e) {
						e.printStackTrace();
					} finally {
						sdfThreadLocal.remove();
					}
				}
			});
		}
	}

	public void test1() {
		for (int i = 0; i < 1000; i++) {
			poolExecutor.execute(new Runnable() {
				@Override
				public void run() {
					SimpleDateFormat simpleDateFormat = sdfThreadLocal.get();

					String dateString = simpleDateFormat.format(new Date());
					try {
						Date parseDate = simpleDateFormat.parse(dateString);
						String dateString2 = simpleDateFormat.format(parseDate);
						System.out.println(dateString.equals(dateString2));
					} catch (ParseException e) {
						e.printStackTrace();
					} finally {
						sdfThreadLocal.remove();
					}
				}
			});
		}
	}

	public static void main(String[] args) {
		// new ThreadLocalDemo().test0();  //线程不安全
		new ThreadLocalDemo().test1();
	}
}
