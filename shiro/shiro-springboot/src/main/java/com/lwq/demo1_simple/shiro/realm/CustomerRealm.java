package com.lwq.demo1_simple.shiro.realm;

import java.util.Arrays;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

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
		// simple，这里假设只有一个用户，不从数据库查了就
		if ("xiaochen".equals(principal)){
			String salt = "QAqa";
			String pwd = new Md5Hash("123", salt, 1024).toString();
			return new SimpleAuthenticationInfo(principal,pwd, ByteSource.Util.bytes(salt), this.getName());
		} else if ("lwq".equals(principal)) {
			String salt = "abcd";
			String pwd = new Md5Hash("123", salt, 1024).toString();
			return new SimpleAuthenticationInfo(principal,pwd, ByteSource.Util.bytes(salt), this.getName());
		} else if ("test".equals(principal)) {
			String salt = "test";
			String pwd = new Md5Hash("456", salt, 1024).toString();
			return new SimpleAuthenticationInfo(principal,pwd, ByteSource.Util.bytes(salt), this.getName());
		}

		return null;
	}

	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//获取身份信息
		String primaryPrincipal = (String) principals.getPrimaryPrincipal();
		System.out.println("调用授权验证: "+primaryPrincipal);
		//根据主身份信息获取角色 和 权限信息

		//授权角色信息
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		if ("xiaochen".equals(primaryPrincipal)) {
			simpleAuthorizationInfo.addRoles(Arrays.asList("admin", "user"));
		} else if ("lwq".equals(primaryPrincipal)) {
			simpleAuthorizationInfo.addRole("user");
		}
		// simpleAuthorizationInfo.addRoles();
		// simpleAuthorizationInfo.addStringPermission();
		// simpleAuthorizationInfo.addStringPermissions();
		// simpleAuthorizationInfo.addObjectPermission();
		// simpleAuthorizationInfo.addObjectPermissions();

		return simpleAuthorizationInfo;

	}


}
