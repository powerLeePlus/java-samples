package com.lwq.spring.aop.annotation.service;

import java.util.List;

import com.lwq.spring.aop.annotation.dto.DepNameDto;
import com.lwq.spring.aop.annotation.dto.GrpNameDto;
import com.lwq.spring.aop.annotation.dto.MemberNameDto;
import com.lwq.spring.aop.annotation.dto.SessionUser;

/**
 * 组织管理业务类
 * @author lwq
 * @date 2022/11/25 0025
 * @since
 */
public interface OrganizationService {

	/**
	 * 获取当前登录用户能看的部门
	 */
	List<DepNameDto> getAuthDeps(SessionUser sessionUser);
	/**
	 * 获取当前登录用户能看的小组
	 */
	List<GrpNameDto> getAuthGrps(SessionUser sessionUser);
	/**
	 * 获取当前登录用户能看的成员
	 * @param sessionUser
	 */
	List<MemberNameDto> getAuthMembers(SessionUser sessionUser);

	/**
	 * 获取当前登录用户能看的小组(需要指定具体哪个部门下的)
	 */
	List<GrpNameDto> getAuthGrps2(SessionUser sessionUser, Integer depId);
	/**
	 * 获取当前登录用户能看的成员(需要指定具体哪个小组下的)
	 * @param sessionUser
	 */
	List<MemberNameDto> getAuthMembers2(SessionUser sessionUser, Integer grpId);
}
