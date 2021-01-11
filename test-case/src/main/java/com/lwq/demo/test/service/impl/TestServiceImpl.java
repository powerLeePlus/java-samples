package com.lwq.demo.test.service.impl;

import org.springframework.stereotype.Service;

import com.lwq.demo.test.service.TestService;

/**
 * @author lwq
 * @date 2021/1/11 0011
 */
@Service
public class TestServiceImpl implements TestService {
	public String test1() {
		return "test1 service";
	}
}
