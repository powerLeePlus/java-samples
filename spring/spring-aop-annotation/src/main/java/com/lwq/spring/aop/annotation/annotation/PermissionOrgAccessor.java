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
 *** 2、根据需要定义方法参数(类型为{@link OrgDto}的具体实现)
 *** 3、根据需要在上步定义的参数上加注解{@link PermissionOrg}
 *** 4、直接使用即可
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionOrgAccessor {
}
