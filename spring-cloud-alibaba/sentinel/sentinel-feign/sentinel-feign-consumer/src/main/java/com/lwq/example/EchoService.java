package com.lwq.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lwq
 * @date 2021/6/2 0002
 */
@FeignClient(value = "sentinel-feign-provider", fallbackFactory = EchoFallbackHandlerFactory.class, fallback = EchoFallbackHandler.class)
public interface EchoService {

	@GetMapping("/echo/{str}")
	public String echo(@PathVariable("str") String str);
}
