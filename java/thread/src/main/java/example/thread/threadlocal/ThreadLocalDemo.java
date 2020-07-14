package example.thread.threadlocal;

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
	 *
	 * 链接：https://www.jianshu.com/p/1a5d288bdaee
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
