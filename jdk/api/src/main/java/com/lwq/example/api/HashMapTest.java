package com.lwq.example.api;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author lwq
 * @date 2022/6/21 0021
 * @since
 */
public class HashMapTest {
	
	/**
	 *
	 */
	static final HashMap<String, String> map = new HashMap<String, String>(2);

	public static void test() {
		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					map.put(UUID.randomUUID().toString(), "");
				}
			}).start();
		}
	}

	public static void main(String[] args) {
		test();
	}
}
