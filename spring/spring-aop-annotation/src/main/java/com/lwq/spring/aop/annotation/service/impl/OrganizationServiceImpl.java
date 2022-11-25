package com.lwq.spring.aop.annotation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lwq.spring.aop.annotation.dto.DepNameDto;
import com.lwq.spring.aop.annotation.dto.GrpNameDto;
import com.lwq.spring.aop.annotation.dto.MemberNameDto;
import com.lwq.spring.aop.annotation.dto.SessionUser;
import com.lwq.spring.aop.annotation.service.OrganizationService;

/**
 * 组织管理业务类
 * @author lwq
 * @date 2022/11/25 0025
 * @since
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {
	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	@Override
	public List<DepNameDto> getAuthDeps(SessionUser sessionUser) {
		logger.debug("getAuthDeps，sessionUser:{}", sessionUser);
		// 这里只是举个例子
		List<DepNameDto> depNameDtos = new ArrayList<>();
		if (sessionUser.getId() == 1) {
			DepNameDto depNameDto1 = new DepNameDto();
			depNameDto1.setId(1);
			depNameDto1.setDepName("一军团");
			DepNameDto depNameDto2 = new DepNameDto();
			depNameDto2.setId(2);
			depNameDto2.setDepName("西安军团");
			depNameDtos.add(depNameDto1);
			depNameDtos.add(depNameDto2);
		} else {
			DepNameDto depNameDto1 = new DepNameDto();
			depNameDto1.setId(3);
			depNameDto1.setDepName("二军团");
			depNameDtos.add(depNameDto1);
		}
		return depNameDtos;
	}

	@Override
	public List<GrpNameDto> getAuthGrps(SessionUser sessionUser) {
		logger.debug("getAuthGrps，sessionUser:{}", sessionUser);
		// 这里只是举个例子
		List<GrpNameDto> grpNameDtos = new ArrayList<>();
		if (sessionUser.getId() == 1) {
			GrpNameDto grpNameDto1 = new GrpNameDto();
			grpNameDto1.setId(1);
			grpNameDto1.setGrpName("爆破组");
			GrpNameDto grpNameDto2 = new GrpNameDto();
			grpNameDto2.setId(2);
			grpNameDto2.setGrpName("排雷组");
			grpNameDtos.add(grpNameDto1);
			grpNameDtos.add(grpNameDto2);
		} else {
			GrpNameDto grpNameDto1 = new GrpNameDto();
			grpNameDto1.setId(3);
			grpNameDto1.setGrpName("突击组");
			grpNameDtos.add(grpNameDto1);
		}
		return grpNameDtos;
	}

	@Override
	public List<MemberNameDto> getAuthMembers(SessionUser sessionUser) {
		logger.debug("getAuthMembers，sessionUser:{}", sessionUser);
		// 这里只是举个例子
		List<MemberNameDto> memberNameDtos = new ArrayList<>();
		if (sessionUser.getId() == 1) {
			MemberNameDto memberNameDto1 = new MemberNameDto();
			memberNameDto1.setId(1);
			memberNameDto1.setName("胡一刀");
			MemberNameDto memberNameDto2 = new MemberNameDto();
			memberNameDto2.setId(2);
			memberNameDto2.setName("曾小贤");
			memberNameDtos.add(memberNameDto1);
			memberNameDtos.add(memberNameDto2);
		} else {
			MemberNameDto memberNameDto1 = new MemberNameDto();
			memberNameDto1.setId(3);
			memberNameDto1.setName("胡咧咧");
			memberNameDtos.add(memberNameDto1);
		}
		return memberNameDtos;
	}
}
