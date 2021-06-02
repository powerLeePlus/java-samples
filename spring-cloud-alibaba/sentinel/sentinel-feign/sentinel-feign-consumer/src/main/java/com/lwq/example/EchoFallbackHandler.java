package com.lwq.example;

/**
 * @author lwq
 * @date 2021/6/2 0002
 */
public class EchoFallbackHandler implements EchoService{
	@Override
	public String echo(String str) {
		return "echo fallback";
	}
}
