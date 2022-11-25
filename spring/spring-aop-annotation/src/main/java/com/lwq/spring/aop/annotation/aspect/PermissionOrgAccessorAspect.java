package com.lwq.spring.aop.annotation.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lwq.spring.aop.annotation.annotation.PermissionOrg;
import com.lwq.spring.aop.annotation.annotation.PermissionOrgAccessor;
import com.lwq.spring.aop.annotation.dto.*;
import com.lwq.spring.aop.annotation.service.OrganizationService;
import com.lwq.spring.aop.annotation.util.SessionUtil;

/**
 * 处理{@link PermissionOrgAccessor}和{@link PermissionOrg}
 * @author lwq
 * @date 2022/11/23 0023
 * @since
 */
@Aspect
@Component
public class PermissionOrgAccessorAspect {
	private static final Logger logger = LoggerFactory.getLogger(PermissionOrgAccessorAspect.class);

	@Resource
	private OrganizationService organizationService;

	/**
	 * 连接标注了{@link PermissionOrgAccessor}注解的方法
	 */
	@Around("@annotation(com.lwq.spring.aop.annotation.annotation.PermissionOrgAccessor)")
	public Object resolvePermissionOrgValue(ProceedingJoinPoint point) throws Throwable {
		logger.debug("aop获取当前登录用户被授权的组织管理成员列表");
		Object[] args = point.getArgs();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		Type[] genericParameterTypes = method.getGenericParameterTypes();
		for (int i = 0; i < parameterAnnotations.length; i++) {
			Annotation[] parameterAnnotation = parameterAnnotations[i];
			for (Annotation annotation : parameterAnnotation) {
				// 只有参数被@PermissionOrg注解的才会处理
				if (annotation.annotationType().equals(PermissionOrg.class)) {
					Type type = genericParameterTypes[i];
					ParameterizedType pt = (ParameterizedType) type;
					Type[] types = pt.getActualTypeArguments();
					Class<? extends OrgDto> clazz = (Class<? extends OrgDto>) types[0];
					SessionUser sessionUser = SessionUtil.getSessionUser();
					if (clazz.equals(DepNameDto.class)) {
						//部门
						args[i] = organizationService.getAuthDeps(sessionUser);
					} else if (clazz.equals(GrpNameDto.class)) {
						//小组
						args[i] = organizationService.getAuthGrps(sessionUser);
					} else if (clazz.equals(MemberNameDto.class)) {
						//成员
						args[i] = organizationService.getAuthMembers(sessionUser);
					}
				}
			}
		}
		return point.proceed(args);
	}

	/**
	 * 以下为其他例子
	 */
	/**
	 * 示例二
	 * 连接标注{@link PermissionOrgAccessor}注解的方法，且有且只有一个类型为{@link DepNameDto}的参数，
	 * 如{@code public void oneDepOperation(DepNameDto depNameDto)}
	 *
	 * @Around("@annotation(com.lwq.spring.aop.annotation.annotation.PermissionOrgAccessor) && args(com.lwq.spring.aop.annotation.dto.DepNameDto)")
	 */
	@Around("@annotation(com.lwq.spring.aop.annotation.annotation.PermissionOrgAccessor) && args(com.lwq.spring.aop.annotation.dto.DepNameDto)")
	public Object resolvePermissionOrgValue2(ProceedingJoinPoint point) throws Throwable {
		logger.debug("aop获取当前登录用户被授权的组织管理成员");
		Object[] args = point.getArgs();
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Class<?>[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> parameterType = parameterTypes[i];
			if (parameterType.equals(DepNameDto.class)) {
				SessionUser sessionUser = SessionUtil.getSessionUser();
				//部门
				args[i] = organizationService.getAuthDeps(sessionUser).get(0);
			}
		}
		return point.proceed(args);
	}

	/**
	 * 示例三
	 * 连接标注{@link PermissionOrgAccessor}注解的方法，且第一个参数必须为类型是{@link DepNameDto}的参数，后面可跟其他若干参数
	 * 如{@code public void oneDepOperation2(DepNameDto depNameDto, String msg)}
	 *
	 * @Around("@annotation(com.lwq.spring.aop.annotation.annotation.PermissionOrgAccessor) && args(com.lwq.spring.aop.annotation.dto.DepNameDto,..)")
	 */

	/**
	 * 示例四
	 * 连接标注{@link PermissionOrgAccessor}注解的方法，且有且只有一个标注了{@link PermissionOrg}的注解的参数（必须在参数class类上注解，直接在参数前面注解无效），
	 * 如{@code public void oneDepOperation(DepNameDto depNameDto)}
	 *
	 * @Around("@annotation(com.lwq.spring.aop.annotation.annotation.PermissionOrgAccessor) && @args(com.lwq.spring.aop.annotation.annotation.PermissionOrg)")
	 *
	 */

	/**
	 * 示例五
	 * 连接标注{@link PermissionOrgAccessor}注解的方法，且第一个参数必须为标注了{@link PermissionOrg}的注解的参数（必须在参数class类上注解，直接在参数前面注解无效），后面可跟其他若干参数
	 * 如{@code public void oneDepOperation2(DepNameDto depNameDto, String msg)}
	 *
	 * @Around("@annotation(com.lwq.spring.aop.annotation.annotation.PermissionOrgAccessor) && @args(com.lwq.spring.aop.annotation.annotation.PermissionOrg,..)")
	 *
	 */

	/**
	 * 示例六
	 * 连接标注{@link PermissionOrgAccessor}注解的方法，且有且只有一个类型为{@link List}的参数
	 * 如{@code public void depOperation(List<DepNameDto> depNameDtos)}
	 *
	 * @Around("@annotation(com.lwq.spring.aop.annotation.annotation.PermissionOrgAccessor)" +
	 * 			" && args(java.util.List))")
	 */

}
