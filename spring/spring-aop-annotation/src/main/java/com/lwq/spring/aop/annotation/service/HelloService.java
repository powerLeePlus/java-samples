package com.lwq.spring.aop.annotation.service;

import java.util.List;

import com.lwq.spring.aop.annotation.dto.DepNameDto;
import com.lwq.spring.aop.annotation.dto.GrpNameDto;
import com.lwq.spring.aop.annotation.dto.MemberNameDto;

/**
 * @author lwq
 * @date 2021/6/9 0009
 */
public interface HelloService {

	public void depOperation(List<DepNameDto> depNameDtos);
	public void grpOperation(String msg, List<GrpNameDto> grpNameDtos);
	public void memberOperation(List<MemberNameDto> memberNameDtos, String msg);

	public void oneDepOperation(DepNameDto depNameDto);
	public void oneDepOperation2(DepNameDto depNameDto, String msg);
}
