package com.lwq.spring.aop.annotation.annotation;

import java.lang.annotation.*;

/**
 * 当前登录用户被授权的组织管理(部门|小组|成员)列表获取
 * @author lwq
 * @date 2022/11/23 0023
 * @since
 *
 * 用处：用以自动赋值当前登录用户被授权的组织管理(部门|小组|成员)列表
 * 使用说明：
 *** 1、在要获取的方法上加注解{@link permiss}；
 *** 2、在方法上加参数(类型为{@link java.util.List},元素类型为{@link OrgDto}的具体实现)
 *** 3、在上步定义的参数上加此注解
 *** 4、直接使用即可
 */
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionOrg {
}
