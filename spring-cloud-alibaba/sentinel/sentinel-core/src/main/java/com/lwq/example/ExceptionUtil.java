package com.lwq.example;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author lwq
 * @date 2021/5/31 0031
 */
public final class ExceptionUtil {

	private ExceptionUtil() {
	}

	/**
	 * 自定义限流处理-方式二（资源类外）
	 * 必须是static方法
	 */
	public static SentinelClientHttpResponse handleException(HttpRequest request,
	                                                         byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
		System.out.println("Oops: " + ex.getClass().getCanonicalName());
		return new SentinelClientHttpResponse("custom block info");
	}

	/**
	 * 自定义限流处理-方式二（资源类外）
	 * 必须是static方法
	 */
	public static void handleException2(BlockException ex) {
		// Do some log here.
		ex.printStackTrace();
		System.out.println("Oops: " + ex.getClass().getCanonicalName());
	}

	/**
	 * 自定义熔断降级处理-方式二（资源类外）
	 * 必须是static方法
	 */
	public static String degradeException() {
		return "custom degrade info";
	}
}
