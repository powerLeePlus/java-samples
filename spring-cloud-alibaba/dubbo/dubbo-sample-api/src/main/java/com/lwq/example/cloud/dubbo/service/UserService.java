package com.lwq.example.cloud.dubbo.service;

import java.util.Collection;

/**
 * @author lwq
 * @date 2021/6/16 0016
 */
public interface UserService {

	boolean save(User user);

	boolean remove(Long userId);

	Collection<User> findAll();

}
