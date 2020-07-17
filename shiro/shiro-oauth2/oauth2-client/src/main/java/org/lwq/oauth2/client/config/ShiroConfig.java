package org.lwq.oauth2.client.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.lwq.oauth2.client.realm.CustomerRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用来整合shiro框架相关的配置类
 * @author lwq
 * @date 2020/7/9 0009
 */
@Configuration
public class ShiroConfig {

	// 1、创建shiroFilter
	// 负责拦截所有请求
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		//1、创建shiro的filter
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		//2、注入安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		//可以：自定义拦截器
		// LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
		// 如：限制同一帐号同时在线的个数
		//filtersMap.put("kickout", kickoutSessionControlFilter());
		// shiroFilterFactoryBean.setFilters(filtersMap);

		// 说明：默认在配置好shiro环境后默认环境中没有对项目中任何资源进行权限控制,只有通过setFilterChainDefinitionMap()才会。
		// 注意：需保证该map有序
		Map<String,String> map =  new LinkedHashMap<>();

		// 生效规则是自上而下的，也就是按声明顺序，先声明的先匹配，匹配到就不继续往下找了。所以，需保证该map有序
		// anon : 匿名，配置不会被拦截的链接
		map.put("/hello", "anon");
		// map.put("/hello.jsp", "anon");
		map.put("/","anon");
		map.put("/index","anon");
		map.put("/index.jsp","anon");

		map.put("/oauth-client/getCode", "anon");
		map.put("/oauth-client/callbackCode", "anon");
		map.put("/oauth-client/getUserInfo", "anon");

		// 配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
		// map.put("/user/logout", "logout");

		// /** 代表拦截项目中一切资源 ; authc 代表shiro中的一个filter的别名（对应DefaultFilter枚举名称）,详细内容看文档的shirofilter列表
		// map.put("/user/**", "authc");
		// user和authc都需要认证，user区别于authc的是如启用RememberMe功能，user不需再登录，而authc仍然需要再登录
		map.put("/**","user");

		// 角色权限 注意：perms，roles，ssl，rest，port这种继承自AuthorizationFilter的filter才会使setUnauthorizedUrl()生效
		// map.put("/index", "roles");
		// map.put("/users.jsp", "roles[user]");

		//默认认证界面（登录）路径 ，如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		// shiroFilterFactoryBean.setLoginUrl("/login.jsp");

		// 登录成功后要跳转的链接
		// shiroFilterFactoryBean.setSuccessUrl("/index");

		// 未授权页面，只有在这里配置FilterChainDefinitionMap中继承自AuthorizationFilter的相关Filter才会使它生效，如roles,perms,ssl等
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized.jsp");


		// 3、配置要拦截的资源，及认证授权规则
		shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

		return shiroFilterFactoryBean;
	}

	// 2、创建web安全管理器
	@Bean
	public DefaultWebSecurityManager webSecurityManager(Realm realm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		defaultWebSecurityManager.setRealm(realm);
		return defaultWebSecurityManager;
	}

	// 3、创建自定义Realm
	@Bean
	public Realm realm() {
		CustomerRealm customerRealm = new CustomerRealm();

		//修改凭证校验匹配器
		// HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		//设置加密算法为md5
		// credentialsMatcher.setHashAlgorithmName("MD5");
		//设置散列次数
		// credentialsMatcher.setHashIterations(1024);
		// customerRealm.setCredentialsMatcher(credentialsMatcher);

		/**
		 * 开启ehcache缓存管理
		 * */
		customerRealm.setCachingEnabled(true);  //开启全局缓存
		customerRealm.setAuthenticationCachingEnabled(true);  //开启认证缓存
		customerRealm.setAuthenticationCacheName("authenticationCache");
		customerRealm.setAuthorizationCachingEnabled(true);  //开启授权缓存
		customerRealm.setAuthorizationCacheName("authorizationCache");

		customerRealm.setCacheManager(ehCacheManager());  //ehcache缓存

		return customerRealm;
	}

	// ehCache缓存管理器
	@Bean
	public CacheManager ehCacheManager() {
		return new EhCacheManager();
	}

}
