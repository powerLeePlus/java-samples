package com.lwq.example.cloud.dubbo.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author lwq
 * @date 2021/6/16 0016
 */
@DubboService(protocol = "dubbo")
public class InMemoryUserService implements UserService {

	private Map<Long, User> usersRepository = new HashMap<>();

	@Override
	public boolean save(User user) {
		return usersRepository.put(user.getId(), user) == null;
	}

	@Override
	public boolean remove(Long userId) {
		return usersRepository.remove(userId) != null;
	}

	@Override
	public Collection<User> findAll() {
		return usersRepository.values();
	}
}
