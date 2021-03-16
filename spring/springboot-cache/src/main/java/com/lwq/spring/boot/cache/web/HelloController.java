package com.lwq.spring.boot.cache.web;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Autowired
	private RedisCacheManager redisCacheManager;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public static final String KEY_PREFIX = "getOne";

	@GetMapping("findAll")
	@Cacheable("mycache")   // 使用缓存
	public String findAll() throws InterruptedException {
		System.out.println("开始");
		TimeUnit.SECONDS.sleep(3);
		System.out.println("结束");
		return "findAll";
	}

	@GetMapping("getOne")
	@Cacheable(value = "mycache", key = "T(com.lwq.spring.boot.cache.web.HelloController).KEY_PREFIX + \":\" + methodName + \":\" +#p0")   // 使用缓存
	public String getOne(Integer id) throws InterruptedException {
		System.out.println("开始");
		TimeUnit.SECONDS.sleep(3);
		System.out.println("结束");
		return "getOne";
	}

	@PutMapping("update")
	@CachePut("mycache")    // 更新缓存
	public String update() throws InterruptedException {
		System.out.println("更新缓存");
		return "update";
	}

	@DeleteMapping("delete")
	@CacheEvict("mycache")    // 删除缓存
	public String delete() throws InterruptedException {
		System.out.println("删除缓存");
		return "delete";
	}

	// 使用代码操作缓存
	@GetMapping("manual")
	public String manual() throws InterruptedException {
		System.out.println("代码操作缓存");
		// ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
		Cache mycache2 = redisCacheManager.getCache("mycache2");

		// 缓存保存
		mycache2.put("aaa", "aaaValue");

		// 缓存获取
		Cache.ValueWrapper aaa = mycache2.get("aaa");
		System.out.println(aaa.get());

		mycache2.put("aaa", "aaaValue2");

		Cache.ValueWrapper aaa2 = mycache2.get("aaa");
		System.out.println(aaa2.get());

		mycache2.put("bbb", "bbbValue");
		Cache.ValueWrapper bbb = mycache2.get("bbb");
		System.out.println(bbb.get());
		// 缓存清除
		mycache2.evict("bbb");

		// 缓存清除
		mycache2.clear();

		return "manual";
	}

}