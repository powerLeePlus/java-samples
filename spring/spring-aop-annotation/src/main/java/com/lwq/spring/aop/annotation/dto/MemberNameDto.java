package com.lwq.spring.aop.annotation.dto;

/**
 * 组织管理-成员
 * @author lwq
 * @date 2022/11/23 0023
 * @since
 */
public class MemberNameDto implements OrgDto {
	/**
	 * 成员ID
	 */
	private Integer id;
	/**
	 * 成员名称
	 */
	private String name;
	/**
	 * 成员邮箱
	 */
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":")
				.append(id);
		sb.append(",\"name\":\"")
				.append(name).append('\"');
		sb.append(",\"email\":\"")
				.append(email).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
