package com.lwq.spring.boot.async.web;

import java.time.LocalTime;
import java.util.concurrent.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.lwq.spring.boot.async.service.HelloService;

@RestController
public class HelloController {

	@Autowired
	private HelloService helloService;

	/**
	 * WEB异步方式一、Callable<?>
	 */
	@GetMapping("test1")
	public Callable<String> test1() throws InterruptedException {
		System.out.println("外层，start，线程：" + Thread.currentThread().getName());
		Callable<String>  returnCallable = () -> {
			System.out.println("内层，开始处理，线程：" + Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(3);
			System.out.println("内层，结束处理，线程：" + Thread.currentThread().getName());

			return "test1";
		};
		System.out.println("外层，end，线程：" + Thread.currentThread().getName());

		return returnCallable;
	}

	/**
	 * WEB异步方式二、DeferredResult<?>
	 *
	 * 与Callable<?>的区别：
	 *      不同的是这一次线程是由我们管理。创建一个线程并将结果set到DeferredResult是由我们自己来做的。
	 *  用completablefuture创建一个异步任务。这将创建一个新的线程，在那里我们的长时间运行的任务将被执行。也就是在这个线程中，我们将set结果到DeferredResult并返回。
	 *  是在哪个线程池中我们取回这个新的线程？默认情况下，在completablefuture的supplyasync方法将在forkjoin池运行任务。如果你想使用一个不同的线程池，你可以通过传一个executor到supplyasync方法：
	 */
	@GetMapping("test2")
	public DeferredResult<String> test2() throws InterruptedException {
		System.out.println("外层，start，线程：" + Thread.currentThread().getName());
		DeferredResult<String> deferredResult = new DeferredResult<>();
		helloService.test2(deferredResult, "test2");
		System.out.println("外层，end，线程：" + Thread.currentThread().getName());

		return deferredResult;
	}

	/**
	 * WEB异步方式三、WebAsyncTask<?>
	 *
	 * 与Callable<?>的区别：
	 *  对Callable<?>做了进一步封装，如，可以指定超时时间；可以自定义Executor线程池
	 *
	 */
	@GetMapping("test3")
	public WebAsyncTask<String> test3() throws InterruptedException {
		System.out.println("外层，start，线程：" + Thread.currentThread().getName());
		WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(6000L, "springTaskExecutor",
				() -> {
					System.out.println("内层，开始处理，线程：" + Thread.currentThread().getName());
					TimeUnit.SECONDS.sleep(3);
					System.out.println("内层，结束处理，线程：" + Thread.currentThread().getName());

					return "test3";
				}
		);
		System.out.println("外层，end，线程：" + Thread.currentThread().getName());

		return webAsyncTask;
	}


	/**
	 * service异步
	 */
	@GetMapping("/echo/{str}")
	public String echo(@PathVariable String str) {
		System.out.println("controller，start，线程：" + Thread.currentThread().getName());
		helloService.echo(str);
		System.out.println("controller，end，线程：" + Thread.currentThread().getName());

		return str;
	}

	@GetMapping("/echo2/{str}")
	public String echo2(@PathVariable String str) throws ExecutionException, InterruptedException {
		System.out.println("controller，start，线程：" + Thread.currentThread().getName());
		Future<String> future = helloService.echo2(str);
		String s = future.get();
		System.out.println("controller，end，线程：" + Thread.currentThread().getName());

		return s;
	}

	@GetMapping("/echo3/{str}")
	public String echo3(@PathVariable String str) throws ExecutionException, InterruptedException {
		System.out.println("controller，start，线程：" + Thread.currentThread().getName() + "，当前时间（分）：" + LocalTime.now());
		CompletableFuture<String> completableFuture1 = helloService.echo3(str + "1");
		CompletableFuture<String> completableFuture2 = helloService.echo3(str + "2");
		CompletableFuture<String> completableFuture3 = helloService.echo3(str + "3");
		CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3).join();
		System.out.println("controller，end，线程：" + Thread.currentThread().getName() + "，当前时间（分）：" + LocalTime.now());

		return completableFuture1.get() + completableFuture2.get() + completableFuture3.get();
	}

	// 通过AsyncRestTemplate调用rest异步接口
	public static void main(String[] args) {
		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
		System.out.println("异步请求start");
		//调用完后立即返回（没有阻塞）
		ListenableFuture<ResponseEntity<String>> forEntity = asyncRestTemplate.getForEntity("http://localhost:8080/test1", String.class);
		//设置异步回调
		forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
			@Override
			public void onFailure(Throwable throwable) {
				System.out.println("请求失败，" + throwable.getMessage());
			}

			@Override
			public void onSuccess(ResponseEntity<String> stringResponseEntity) {
				System.out.println("请求结果：" + stringResponseEntity.getBody());
			}
		});
		System.out.println("异步请求end");
	}
}