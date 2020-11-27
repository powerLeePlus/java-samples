package example.multithread.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * SimpleDateFormat线程安全测试
 * 线程不安全
 */
public class SimpleDateFormatTest {
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
					}
				}
			});
		}
	}

	public static void main(String[] args) {
		// new SimpleDateFormatTest().test0();
		// 执行结果，会有false出现，说明线程不安全。

		/** 如何保证线程安全呢
		 * 1、将SimpleDateFormat定义成局部变量(每个线程中)，见 见 {@link #test1() }
		 * 2、加锁：synchronized(lock), 见 见 {@link #test2() }
		 * 3、使用DateTimeFormatter代替SimpleDateFormat,  见 {@link #test3() }
		 * 4、使用ThreadLocal , 见 {@link ThreadLocalDemo}
		 */

		// new SimpleDateFormatTest().test1();
		// new SimpleDateFormatTest().test2();
		new SimpleDateFormatTest().test3();


	}

	public void test1() {
		for (int i = 0; i < 1000; i++) {
			poolExecutor.execute(new Runnable() {
				@Override
				public void run() {
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					String dateString = simpleDateFormat.format(new Date());
					try {
						Date parseDate = simpleDateFormat.parse(dateString);
						String dateString2 = simpleDateFormat.format(parseDate);
						System.out.println(dateString.equals(dateString2));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	public void test2() {
		for (int i = 0; i < 1000; i++) {
			poolExecutor.execute(new Runnable() {
				@Override
				public void run() {
					synchronized (simpleDateFormat) {

						String dateString = simpleDateFormat.format(new Date());
						try {
							Date parseDate = simpleDateFormat.parse(dateString);
							String dateString2 = simpleDateFormat.format(parseDate);
							System.out.println(dateString.equals(dateString2));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}

	public void test3() {
		for (int i = 0; i < 1000; i++) {
			poolExecutor.execute(new Runnable() {
				@Override
				public void run() {

					LocalDateTime now = LocalDateTime.now();

					String dateStr1 = now.format(dateTimeFormatter);
					LocalDateTime now2 = LocalDateTime.parse(dateStr1, dateTimeFormatter);
					String dateStr2 = now2.format(dateTimeFormatter);

					System.out.println(Objects.equals(dateStr1, dateStr2));

				}
			});
		}
	}
}