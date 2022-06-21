package org.lwq.impl;

import org.lwq.SPIService;

/**
 * @author lwq
 * @date 2020/7/15 0015
 */
public class SPIServiceImpl1 implements SPIService {
	@Override
	public void invoke() {
		System.out.println("SPIService实现1");
	}
}
