package com.lwq.spring.aop.annotation.dto;

/**
 * @author lwq
 * @date 2022/11/25 0025
 * @since
 */
public class SessionUser {
	private Integer id;
	private String username;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":")
				.append(id);
		sb.append(",\"username\":\"")
				.append(username).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
