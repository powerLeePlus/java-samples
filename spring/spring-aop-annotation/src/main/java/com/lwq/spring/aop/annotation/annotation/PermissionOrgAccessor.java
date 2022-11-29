package com.lwq.spring.aop.annotation.annotation;

import java.lang.annotation.*;

/**
 * 当前登录用户被授权的组织管理(部门|小组|成员|成员ID)列表获取声明
 * @author lwq
 * @date 2022/11/24 0023
 * @since
 *
 * 用处：用以自动赋值当前登录用户被授权的组织管理(部门|小组|成员)列表获取
 * 使用说明：
 *** 1、加在要获取的方法上；
 *** 2、根据需要定义方法参数(参数类型要求参见{@link PermissionOrg}中说明)
 *** 3、根据需要在上步定义的参数上加注解{@link PermissionOrg}
 *** 4、调用时该参数处理（参见{@link PermissionOrg}中说明）
 *** 5、直接使用即可
 *
 * 注意：@PermissionOrg注解用在Controller方法上也行，
 *** 只是需要注意：如果要自动赋值的就是该参数，需要通过注解@RequestParam(required = false)表明该参数可为null(可不传)，否则会报错
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionOrgAccessor {
}
