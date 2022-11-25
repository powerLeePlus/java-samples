package com.lwq.spring.aop.annotation.dto;

/**
 * c组织管理-部门
 * @author lwq
 * @date 2022/11/23 0023
 * @since
 */
// @PermissionOrg
public class DepNameDto implements OrgDto {
	/**
	 * 部门ID
	 */
	private Integer id;
	/**
	 * 部门名称
	 */
	private String depName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":")
				.append(id);
		sb.append(",\"depName\":\"")
				.append(depName).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
