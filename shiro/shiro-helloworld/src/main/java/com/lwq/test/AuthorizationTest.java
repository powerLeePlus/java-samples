package com.lwq.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;

import com.lwq.realm.CustomerRealm;

/**
 * 授权
 * 授权，即访问控制，控制谁能访问哪些资源。主体进行身份认证后需要分配权限方可访问系统的资源，对于某些资源没有权限是无法访问的。
 *
 * @author lwq
 * @date 2020/7/9 0009
 */
public class AuthorizationTest {

	public static void main(String[] args) {
		//创建securityManager
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		//IniRealm realm = new IniRealm("classpath:shiro.ini");
		//设置为自定义realm获取认证数据
		CustomerRealm customerRealm = new CustomerRealm();
		//设置md5加密
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("MD5");
		credentialsMatcher.setHashIterations(1024);//设置散列次数
		customerRealm.setCredentialsMatcher(credentialsMatcher);
		defaultSecurityManager.setRealm(customerRealm);
		//将安装工具类中设置默认安全管理器
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		//获取主体对象
		Subject subject = SecurityUtils.getSubject();
		//创建token令牌
		UsernamePasswordToken token = new UsernamePasswordToken("xiaochen", "123");
		try {
			subject.login(token);//用户登录
			System.out.println("登录成功~~");
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			System.out.println("用户名错误!!");
		}catch (IncorrectCredentialsException e){
			e.printStackTrace();
			System.out.println("密码错误!!!");
		}
		//认证通过，进行授权
		if(subject.isAuthenticated()){
			// 基于角色权限管理
			boolean admin = subject.hasRole("admin");
			if (admin) {
				System.out.println(admin);
			} else {
				System.err.println(admin);
			}

			// 基于资源
			// boolean permitted = subject.isPermitted("product:create:001");
			boolean permitted = subject.isPermitted("product1:create:001");
			if (permitted) {
				System.out.println(permitted);
			} else {
				System.err.println(permitted);
			}
		}
	}
}
