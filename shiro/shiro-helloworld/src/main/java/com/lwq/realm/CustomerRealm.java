package com.lwq.realm;

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
		String principal = (String) token.getPrincipal();

		/*if("xiaochen".equals(principal)){
			return new SimpleAuthenticationInfo(principal,"123",this.getName());
		}*/

		// 实际应用是将盐和散列后的值存在数据库中，自动realm从数据库取出盐和加密后的值由shiro完成密码校验。
		// md5 + salt
		if("xiaochen".equals(principal)){
			String salt = "Q4F%";  // 盐

			String password = new Md5Hash("123", salt, 1024).toString();  // 等于"3c88b338102c1a343bcb88cd3878758e"
			// String password = "3c88b338102c1a343bcb88cd3878758e";  // 保存在数据库的md5+salt之后的密码

			return new SimpleAuthenticationInfo(principal,password,
					ByteSource.Util.bytes(salt),this.getName());
		}

		return null;
	}

	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String primaryPrincipal = (String) principals.getPrimaryPrincipal();
		System.out.println("primaryPrincipal = " + primaryPrincipal);

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		// 基于角色
		simpleAuthorizationInfo.addRole("admin");

		// 基于资源
		simpleAuthorizationInfo.addStringPermission("user:update:*");
		simpleAuthorizationInfo.addStringPermission("product:*:*");

		return simpleAuthorizationInfo;
	}


}
