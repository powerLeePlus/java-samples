package com.lwq.spring.aop.annotation.util;

import java.util.Random;

import com.lwq.spring.aop.annotation.dto.SessionUser;

/**
 * @author lwq
 * @date 2022/11/25 0025
 * @since
 */
public class SessionUtil {

	public static SessionUser getSessionUser() {
		// 获取登录用户，这里只是举个例子
		Random random = new Random();
		int nextInt = random.nextInt(3);
		SessionUser sessionUser = new SessionUser();
		if (nextInt == 1) {
			sessionUser.setId(1);
			sessionUser.setUsername("小强");
		} else {
			sessionUser.setId(2);
			sessionUser.setUsername("大马");
		}
		return sessionUser;
	}
}
