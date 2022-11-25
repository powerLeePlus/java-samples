package com.lwq.spring.aop.annotation.dto;

/**
 * 组织管理-小组
 * @author lwq
 * @date 2022/11/23 0023
 * @since
 */
public class GrpNameDto implements OrgDto {
	/**
	 * 小组ID
	 */
	private Integer id;
	/**
	 * 小组名称
	 */
	private String grpName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("\"id\":")
				.append(id);
		sb.append(",\"grpName\":\"")
				.append(grpName).append('\"');
		sb.append('}');
		return sb.toString();
	}
}
