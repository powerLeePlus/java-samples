package com.lwq.spring.aop.annotation.annotation;

import java.lang.annotation.*;

import org.springframework.core.annotation.AliasFor;

import com.lwq.spring.aop.annotation.dto.OrgDto;

/**
 * 当前登录用户被授权的组织管理(部门|小组|成员)列表获取
 * @author lwq
 * @date 2022/11/23 0023
 * @since
 *
 * 用处：用以自动赋值当前登录用户被授权的组织管理(部门|小组|成员)列表
 * 使用说明：
 *** 1、在要获取的方法上加注解{@link PermissionOrgAccessor}；
 *** 2、在方法上加参数 ->
 ******  (1)、如果要自动赋值的就是该参数：则，类型为{@link java.util.List},元素类型为{@link OrgDto}的具体实现
 ******  (2)、如果要自动赋值的是该参数类中的某个field：则，该参数类中的那个field类型为{@link java.util.List},元素类型为{@link OrgDto}的具体实现)
 *** 3、在上步定义的参数上加此注解
 *** 4、调用时该参数处理 ->
 ******* (1)、如果要自动赋值的就是该参数：则，传null即可（如果该参数的赋值查询依赖其他参数，则需要指定该参数依赖的参数所在位置（从0开始））
 ******* (2)、如果要自动赋值的是该参数类中的某个field：则，该参数不能为null,正常传值即可（要自动赋值的那个field为null即可）
 *** 5、直接使用即可
 */
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionOrg {
	/**
	 * 如果要赋值的参数的查询需要依赖其他参数，
	 * 则该参数指明以来的参数所在位置（从0开始）
	 */
	int value() default -1;
	@AliasFor("value")
	int dependParamIndex() default -1;

	/**
	 * 如果要自动赋值的是此注解的参数类上的某个field，
	 * 则该属性指明这个field的name
	 */
	String paramField() default "";

}
