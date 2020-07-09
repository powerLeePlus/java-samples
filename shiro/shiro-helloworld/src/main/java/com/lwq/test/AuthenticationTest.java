package com.lwq.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.lwq.realm.CustomerRealm;

/**
 * 认证
 * 身份认证，就是判断一个用户是否为合法用户的处理过程。最常用的简单身份认证方式是系统通过核对用户输入的用户名和口令，看其是否与系统中存储的该用户的用户名和口令一致，来判断用户身份是否正确。
 *
 * @author lwq
 * @date 2020/7/9 0009
 */
public class AuthenticationTest {

	/**
	 * 使用的是shiro默认的Realm SimpleAccountRealm
	 * @Author: lwq
	 * @Date: 2020/7/9 0009 11:07
	 * @Param:
	 * @Return: void
	 */
	@Test
	public void testHelloworld() {
		//创建securityManager
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		defaultSecurityManager.setRealm(new IniRealm("classpath:shiro.ini"));
		//将安装工具类中设置默认安全管理器
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		//获取主体对象
		Subject subject = SecurityUtils.getSubject();
		//创建token令牌
		// UsernamePasswordToken token = new UsernamePasswordToken("xiaochen1", "123");
		// UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "1111");
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

		/**
		 * UnknownAccountException （用户名错误）
		 * IncorrectCredentialsException （密码错误）
		 * DisabledAccountException（帐号被禁用）
		 * LockedAccountException（帐号被锁定）
		 * ExcessiveAttemptsException（登录失败次数过多）
		 * ExpiredCredentialsException（凭证过期）等
		 **/
	}

	@Test
	public void testCustomerRealm() {
		//创建securityManager
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		//IniRealm realm = new IniRealm("classpath:shiro.ini");
		//设置为自定义realm获取认证数据
		defaultSecurityManager.setRealm(new CustomerRealm());
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
		} catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			System.out.println("密码错误!!!");
		}
	}

	@Test
	public void testCustomerRealmWithMD5$Salt() {
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
	}

}
