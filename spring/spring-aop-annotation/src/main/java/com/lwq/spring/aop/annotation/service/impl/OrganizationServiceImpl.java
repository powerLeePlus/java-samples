package com.lwq.spring.aop.annotation.service.impl;

import java.util.*;

import javax.annotation.PostConstruct;

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

	/**
	 * 部门
	 * Integer 部门ID
	 */
	private static final Map<Integer, DepNameDto> DEP_NAME_DTOS = new HashMap<>();
	/**
	 * 小组
	 * Integer 小组ID
	 */
	private static final Map<Integer, GrpNameDto> GRP_NAME_DTOS = new HashMap<>();
	/**
	 * 成员
	 * Integer 成员ID
	 */
	private static final Map<Integer, MemberNameDto> MEMBER_NAME_DTOS = new HashMap<>();

	/**
	 * 用户能查看的部门ID
	 * Integer : 用户ID
	 * List<Integer> : 部门集合
	 */
	private static final Map<Integer, List<Integer>> USER_DEPS = new HashMap<>();
	/**
	 * 用户能查看的小组ID
	 * Integer : 用户ID
	 * List<Integer> : 小组集合
	 */
	private static final Map<Integer, List<Integer>> USER_GRPS = new HashMap<>();

	/**
	 * 用户能查看的成员ID
	 * Integer : 用户ID
	 * List<Integer> : 成员集合
	 */
	private static final Map<Integer, List<Integer>> USER_MEMBERS = new HashMap<>();

	@PostConstruct
	public void initData() {
		initDeps();
		initGrps();
		initMembers();
		initUserAuth();
	}

	public void initDeps() {
		for (int i = 1; i < 4; i++) {
			DepNameDto depNameDto = new DepNameDto();
			depNameDto.setId(i);
			depNameDto.setDepName("部门" + i);
			DEP_NAME_DTOS.put(i, depNameDto);
		}
	}
	public void initGrps() {
		for (int i = 1; i < 5; i++) {
			GrpNameDto grpNameDto = new GrpNameDto();
			grpNameDto.setId(i);
			grpNameDto.setGrpName("小组" + i);
			Random random = new Random();
			int depId = random.nextInt(3) + 1;
			grpNameDto.setDepId(depId);
			GRP_NAME_DTOS.put(i, grpNameDto);
		}
	}
	public void initMembers() {
		for (int i = 1; i < 7; i++) {
			MemberNameDto memberNameDto = new MemberNameDto();
			memberNameDto.setId(i);
			memberNameDto.setName("成员" + i);
			Random random = new Random();
			int grpId = random.nextInt(4) + 1;
			memberNameDto.setGrpId(grpId);
			MEMBER_NAME_DTOS.put(i, memberNameDto);
		}
	}

	/**
	 * 初始化登录用户有权限查看的部门|小组|成员
	 */
	public void initUserAuth() {
		USER_DEPS.put(1, Arrays.asList(1, 2));
		USER_DEPS.put(2, Arrays.asList(3));

		USER_GRPS.put(1, Arrays.asList(1, 2));
		USER_GRPS.put(2, Arrays.asList(3, 4));

		USER_MEMBERS.put(1, Arrays.asList(1, 2, 3, 4));
		USER_MEMBERS.put(2, Arrays.asList(5, 6));
	}

		@Override
	public List<DepNameDto> getAuthDeps(SessionUser sessionUser) {
		logger.debug("getAuthDeps，sessionUser:{}", sessionUser);
		// 这里只是举个例子
		List<DepNameDto> depNameDtos = new ArrayList<>();
		for (Integer depId : USER_DEPS.get(sessionUser.getId())) {
			depNameDtos.add(DEP_NAME_DTOS.get(depId));
		}
		return depNameDtos;
	}

	@Override
	public List<GrpNameDto> getAuthGrps(SessionUser sessionUser) {
		logger.debug("getAuthGrps，sessionUser:{}", sessionUser);
		// 这里只是举个例子
		List<GrpNameDto> grpNameDtos = new ArrayList<>();
		for (Integer grpId : USER_GRPS.get(sessionUser.getId())) {
			grpNameDtos.add(GRP_NAME_DTOS.get(grpId));
		}
		return grpNameDtos;
	}

	@Override
	public List<MemberNameDto> getAuthMembers(SessionUser sessionUser) {
		logger.debug("getAuthMembers，sessionUser:{}", sessionUser);
		// 这里只是举个例子
		List<MemberNameDto> memberNameDtos = new ArrayList<>();
		for (Integer memberId : USER_MEMBERS.get(sessionUser.getId())) {
			memberNameDtos.add(MEMBER_NAME_DTOS.get(memberId));
		}
		return memberNameDtos;
	}

	@Override
	public List<GrpNameDto> getAuthGrps2(SessionUser sessionUser, Integer depId) {
		logger.debug("getAuthGrps，sessionUser:{}", sessionUser);
		// 这里只是举个例子
		List<GrpNameDto> grpNameDtos = new ArrayList<>();
		for (Integer grpId : USER_GRPS.get(sessionUser.getId())) {
			GrpNameDto grpNameDto = GRP_NAME_DTOS.get(grpId);
			if (grpNameDto != null) {
				if (depId.equals(grpNameDto.getDepId())) {
					grpNameDtos.add(grpNameDto);
				}
			}
		}
		return grpNameDtos;
	}

	@Override
	public List<MemberNameDto> getAuthMembers2(SessionUser sessionUser, Integer grpId) {
		logger.debug("getAuthMembers，sessionUser:{}", sessionUser);
		// 这里只是举个例子
		List<MemberNameDto> memberNameDtos = new ArrayList<>();
		for (Integer memberId : USER_MEMBERS.get(sessionUser.getId())) {
			MemberNameDto memberNameDto = MEMBER_NAME_DTOS.get(memberId);
			if (memberNameDto != null) {
				if (grpId.equals(memberNameDto.getGrpId())) {
					memberNameDtos.add(memberNameDto);
				}
			}
		}
		return memberNameDtos;
	}
}
