package com.lwq.spring.aop.annotation.dto;

import java.util.List;

/**
 * @author lwq
 * @date 2022/11/29 0029
 * @since
 */
public class GrpSearceDto {

	private String name;

	private List<GrpNameDto> grpNameDtos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GrpNameDto> getGrpNameDtos() {
		return grpNameDtos;
	}

	public void setGrpNameDtos(List<GrpNameDto> grpNameDtos) {
		this.grpNameDtos = grpNameDtos;
	}
}
