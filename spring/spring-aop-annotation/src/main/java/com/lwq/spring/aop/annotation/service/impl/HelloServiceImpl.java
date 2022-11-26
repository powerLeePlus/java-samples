package com.lwq.spring.aop.annotation.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lwq.spring.aop.annotation.annotation.PermissionOrg;
import com.lwq.spring.aop.annotation.annotation.PermissionOrgAccessor;
import com.lwq.spring.aop.annotation.dto.DepNameDto;
import com.lwq.spring.aop.annotation.dto.GrpNameDto;
import com.lwq.spring.aop.annotation.dto.MemberNameDto;
import com.lwq.spring.aop.annotation.service.HelloService;

/**
 * @author lwq
 * @date 2021/6/9 0009
 */
@Service
public class HelloServiceImpl implements HelloService {
	private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

	@PermissionOrgAccessor
	@Override
	public void depOperation(@PermissionOrg List<DepNameDto> depNameDtos) {
		logger.info("depOperation->我直接就拿到dep信息了:{}", depNameDtos);
	}

	@PermissionOrgAccessor
	@Override
	public void grpOperation(String msg, @PermissionOrg() List<GrpNameDto> grpNameDtos) {
		logger.info("grpOperation->我直接就拿到grp信息了:{}，其他处理:{}", grpNameDtos, msg);
	}

	@PermissionOrgAccessor
	@Override
	public void memberOperation(@PermissionOrg List<MemberNameDto> memberNameDtos, String msg) {
		logger.info("memberOperation->我直接就拿到member信息了:{}，其他处理:{}", memberNameDtos, msg);
	}

	/**
	 * 要想自动注入参数：
	 * @param grpNameDtos 的值，需要依赖参数：
	 * @param depId，则需要在 {@link PermissionOrg}注解中指明该参数所在位置，即：2
	 */
	@PermissionOrgAccessor
	@Override
	public void grpOperation2(String msg, @PermissionOrg(2) List<GrpNameDto> grpNameDtos, Integer depId) {
		logger.info("grpOperation->我直接就拿到grp信息了:{}，其他处理:{}", grpNameDtos, msg);
	}

	/**
	 * 要想自动注入参数：
	 * @param memberNameDtos 的值，需要依赖参数：
	 * @param grpId，则需要在 {@link PermissionOrg}注解中指明该参数所在位置，即：0
	 */
	@PermissionOrgAccessor
	@Override
	public void memberOperation2(Integer grpId, @PermissionOrg(0) List<MemberNameDto> memberNameDtos, String msg) {
		logger.info("memberOperation->我直接就拿到member信息了:{}，其他处理:{}", memberNameDtos, msg);
	}


	@PermissionOrgAccessor
	@Override
	public void oneDepOperation(DepNameDto depNameDto) {
		logger.info("oneDepOperation->我直接就拿到dep信息了:{}", depNameDto);
	}

	@PermissionOrgAccessor
	@Override
	public void oneDepOperation2(DepNameDto depNameDto, String msg) {
		logger.info("oneDepOperation2->我直接就拿到dep信息了:{}，其他处理:{}", depNameDto, msg);
	}
}
