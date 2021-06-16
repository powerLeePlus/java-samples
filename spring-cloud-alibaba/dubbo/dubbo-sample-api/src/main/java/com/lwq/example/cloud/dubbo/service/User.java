package com.lwq.example.cloud.dubbo.service;

import java.io.Serializable;

/**
 * @author lwq
 * @date 2021/6/16 0016
 */
public class User implements Serializable {

	private Long id;

	private String name;

	private Integer age;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":")
				.append(id);
		sb.append(",\"name\":\"")
				.append(name).append('\"');
		sb.append(",\"age\":")
				.append(age);
		sb.append('}');
		return sb.toString();
	}
}
