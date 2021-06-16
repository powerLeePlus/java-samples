package com.lwq.example.cloud.dubbo.service;

import java.util.Map;

/**
 * @author lwq
 * @date 2021/6/16 0016
 */
public interface RestService {
	String param(String param);

	String params(int a, String b);

	String headers(String header, String header2, Integer param);

	String pathVariables(String path1, String path2, String param);

	String form(String form);

	User requestBodyMap(Map<String, Object> data, String param);

	Map<String, Object> requestBodyUser(User user);
}
