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
	public void grpOperation(String msg, @PermissionOrg List<GrpNameDto> grpNameDtos) {
		logger.info("grpOperation->我直接就拿到grp信息了:{}，其他处理:{}", grpNameDtos, msg);
	}

	@PermissionOrgAccessor
	@Override
	public void memberOperation(@PermissionOrg List<MemberNameDto> memberNameDtos, String msg) {
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
