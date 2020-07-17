package org.lwq.oauth2.client.realm;

import java.util.Arrays;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm
 * @author lwq
 * @date 2020/7/9 0009
 */
public class CustomerRealm extends AuthorizingRealm {

	/** 认证方法
	 *
	 * @Author: lwq
	 * @Date: 2020/7/9 0009 11:10
	 * @Param: token
	 * @Return: org.apache.shiro.authc.AuthenticationInfo
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		System.out.println("==========================");
		String principal = (String) token.getPrincipal();
		// 从数据库查询用户信息
		//根据身份信息查询
		if ("lwq".equals(principal)) {
			return new SimpleAuthenticationInfo(principal, "123", getName());
		} else if ("admin".equals(principal)) {
			return new SimpleAuthenticationInfo(principal, "123", getName());
		} else if ("test".equals(principal)) {
			return new SimpleAuthenticationInfo(principal, "123", getName());
		}

		return null;
	}

	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String primaryPrincipal = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		if ("lwq".equals(primaryPrincipal)) {
			simpleAuthorizationInfo.addRole("user");
		} else if ("admin".equals(primaryPrincipal)){
			simpleAuthorizationInfo.addRoles(Arrays.asList("admin", "user"));
		}
		return simpleAuthorizationInfo;

	}


}
