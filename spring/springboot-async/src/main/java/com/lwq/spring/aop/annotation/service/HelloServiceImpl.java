package com.lwq.spring.aop.annotation.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author lwq
 * @date 2021/6/9 0009
 */
@Service
public class HelloServiceImpl implements HelloService {

	/**
	 * 1、@Async使用，返回值：void
	 */
	@Async
	@Override
	public void echo(String str) {
		System.out.println("service，@Async，echo:" + str + "，线程：" + Thread.currentThread().getName());
	}

	/**
	 * 2、@Async使用，返回值：Future<?>
	 */
	@Async
	@Override
	public Future<String> echo2(String str) throws InterruptedException {
		System.out.println("service，@Async，echo:" + str + "，线程：" + Thread.currentThread().getName());
		TimeUnit.SECONDS.sleep(5);
		return new AsyncResult<>(str);
	}

	/**
	 * 3、@Async使用，返回值：CompletableFuture<?>
	 */
	@Async
	@Override
	public CompletableFuture<String> echo3(String str) throws InterruptedException {
		System.out.println("service，@Async，echo:" + str + "，线程：" + Thread.currentThread().getName());
		TimeUnit.SECONDS.sleep(5);
		return CompletableFuture.completedFuture(str);
	}

	@Override
	public void test2(DeferredResult<String> deferredResult, String str) throws InterruptedException {
		CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
				System.out.println("service，DeferredResult，echo:" + str + "，线程：" + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return str;
		}).whenCompleteAsync((result, throwable) -> deferredResult.setResult(str));

	}
}
