package example.thread.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lwq
 */
@Service
@Slf4j
public class ThreadPoolExecutorDemo1 {

	private static final int CORE_POOL_SIZE = 3;

	public volatile static ThreadPoolExecutor executorHandler;

	@PreDestroy
	public synchronized static void closeInitThreadPool() {
		if (executorHandler != null) {
			executorHandler.shutdown();
			executorHandler = null;
			log.info(">>> shutdown init archive thread pool");
		}
	}

	@PostConstruct
	public synchronized static void createInitThreadPool() {
		if (executorHandler == null) {
			createInitThreadPool0();
		} else {
			if (executorHandler.isShutdown()) {
				createInitThreadPool0();
			}
		}
	}

	private static void createInitThreadPool0() {
		int processorNum = Runtime.getRuntime().availableProcessors();
		log.info(">>> runtime availableProcessors:{}", processorNum);

		/*
		// 一、corePoolSize = 0，allowCoreThreadTimeOut=false（默认），其他前提（keepAliveTime > 0; workQueue有界）
		executorHandler = new ThreadPoolExecutor(0, processorNum > CORE_POOL_SIZE ? 2 * processorNum :
				2 * CORE_POOL_SIZE
				, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(2),
				new HandlerThreadFactory("my-thread-pool-"));
		//executorHandler.allowCoreThreadTimeOut(true);*/

		// 二、corePoolSize > 0，allowCoreThreadTimeOut=true
		executorHandler = new ThreadPoolExecutor(3, processorNum > CORE_POOL_SIZE ? 2 * processorNum :
				2 * CORE_POOL_SIZE
				, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(2),
				new HandlerThreadFactory("my-thread-pool-"));
		executorHandler.allowCoreThreadTimeOut(true);

	}

	public String test() {
		String before = "before: ==>" + executorHandler.toString();
		System.out.println(before);
		executorHandler.execute(() -> {
			try {
				System.out.println("执行了");
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		String after = "after: ==>" + executorHandler.toString();
		System.out.println(after);

		return before + "<br>===<br>" + after;

		/**
		 * 测试结论：jdk1.8
		 * 一、corePoolSize = 0，allowCoreThreadTimeOut=false（默认），其他前提（keepAliveTime > 0; workQueue有界）
		 *      有新的任务进来就会创建线程执行（不会等到队列满了），
		 *      接着来新的任务，会等队列满了才新建线程。
		 *      线程（所有）满足keepAliveTime，被回收，最终线程数会为0
		 * 二、corePoolSize > 0，allowCoreThreadTimeOut=true，
		 *      有新的任务进来就会创建线程执行（不会等到队列满了），
		 *      接着来新的任务，会判断线程数是否小于corePoolSize，若是，会直接创建线程(不管有没有空闲线程)，否则，有空闲线程，使用空闲线程，没有空闲线程，加入队列，会等队列满了才新建线程。
		 *      线程（所有）满足keepAliveTime，被回收，最终线程数会为0
		 *      下次仍然满足上面流程
		 */
	}

}
