package com.lwq.spring.aop.annotation.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author lwq
 * @date 2021/6/9 0009
 */
public interface HelloService {

	public void echo(String str);

	public Future<String> echo2(String str) throws InterruptedException;

	public CompletableFuture<String> echo3(String str) throws InterruptedException;

	public void test2(DeferredResult<String> deferredResult, String str) throws InterruptedException;

}
