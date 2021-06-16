package com.lwq.example.cloud.dubbo.bootstrap;

import org.apache.dubbo.config.annotation.DubboService;

import com.lwq.example.cloud.dubbo.service.EchoService;

/**
 * @author lwq
 * @date 2021/6/16 0016
 */
@DubboService
public class EchoServiceImple implements EchoService {

	@Override
	public String echo(String message) {
		return "[echo] Hello, " + message;
	}
}
