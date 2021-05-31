package com.lwq.example;

import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author lwq
 * @date 2021/5/31 0031
 */
@Service
public class TestService {

	// blockHandler 是位于 ExceptionUtil 类下的 handleException 静态方法，需符合对应的类型限制.
	@SentinelResource(value = "test2", blockHandler = "handleException2", blockHandlerClass = ExceptionUtil.class)
	public void test2() {
		System.out.println("Test");
	}

	// blockHandler 是位于当前类下的 exceptionHandler 方法，需符合对应的类型限制.
	@SentinelResource(value = "hello2", blockHandler = "exceptionHandler")
	public String hello2(long s) {
		return String.format("hello at %d", s);
	}

	/**
	 * 自定义限流处理-方式一（资源类内）
	 * 参数和返回必须和资源一致
	 */
	public String exceptionHandler(long s, BlockException ex) {
		// Do some log here.
		ex.printStackTrace();
		return "Oops, error occurred at " + s;
	}
}
