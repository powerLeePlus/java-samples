package com.lwq.example.cloud.bus;

/**
 * @author lwq
 * @date 2021/6/30 0030
 */
public class User {

	private Long id;

	private String name;

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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":")
				.append(id);
		sb.append(",\"name\":\"")
				.append(name).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
