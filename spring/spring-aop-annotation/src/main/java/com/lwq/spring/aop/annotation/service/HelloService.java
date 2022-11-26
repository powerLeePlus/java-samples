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

	/**
	 * 自动注入，不依赖其他参数 start
	 */
	public void depOperation(List<DepNameDto> depNameDtos);
	public void grpOperation(String msg, List<GrpNameDto> grpNameDtos);
	public void memberOperation(List<MemberNameDto> memberNameDtos, String msg);
	/**
	 * 自动注入，不依赖其他参数 end
	 */

	/**
	 * 自动注入，依赖其他参数 start
	 */
	public void grpOperation2(String msg, List<GrpNameDto> grpNameDtos, Integer depId);
	public void memberOperation2(Integer grpId, List<MemberNameDto> memberNameDtos, String msg);
	/**
	 * 自动注入，依赖其他参数 end
	 */

	/**
	 * 自动注入，不依赖其他参数，参数类型class上注解拦截 start
	 */
	public void oneDepOperation(DepNameDto depNameDto);
	public void oneDepOperation2(DepNameDto depNameDto, String msg);
	/**
	 * 自动注入，不依赖其他参数，参数类型class上注解拦截 end
	 */
}
