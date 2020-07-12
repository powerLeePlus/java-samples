package demo2.config;

import demo2.shiro.realm.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Filter;

/**
 * 用来整合shiro框架相关的配置类
 * @author lwq
 * @date 2020/7/9 0009
 */
@Configuration
@DependsOn("redisCacheManagerConfig")
public class ShiroConfig {

	@Autowired
	private RedisCacheManager redisCacheManager;
	@Autowired
	private RedisSessionDAO redisSessionDAO;

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
		map.put("/hello.jsp", "anon");
		map.put("/user/login","anon");
		map.put("/","anon");
		map.put("/index","anon");
		map.put("/index.jsp","anon");
		map.put("/user/register", "anon");
		map.put("/register.jsp", "anon");

		// 配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
		// map.put("/user/logout", "logout");

		map.put("/user/**", "authc");
		// /** 代表拦截项目中一切资源 ; authc 代表shiro中的一个filter的别名（对应DefaultFilter枚举名称）,详细内容看文档的shirofilter列表
		map.put("/**","user");

		// 角色权限 注意：perms，roles，ssl，rest，port这种继承自AuthorizationFilter的filter才会使setUnauthorizedUrl()生效
		map.put("/index", "roles");
		map.put("/users.jsp", "roles[user]");

		//默认认证界面（登录）路径 ，如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login.jsp");

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
		// 将自定义的realm交给SecurityManager管理
		defaultWebSecurityManager.setRealm(realm);

		// 自定义缓存实现 使用redis
		defaultWebSecurityManager.setCacheManager(redisCacheManager);
		// 自定义session管理 使用redis
		defaultWebSecurityManager.setSessionManager(SessionManager());
		// 配置记住我
		defaultWebSecurityManager.setRememberMeManager(rememberMeManager());

		return defaultWebSecurityManager;
	}

	// 3、创建自定义Realm
	@Bean
	public Realm realm() {
		CustomerRealm customerRealm = new CustomerRealm();

		//修改凭证校验匹配器
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		//设置加密算法为md5
		credentialsMatcher.setHashAlgorithmName("MD5");
		//设置散列次数
		credentialsMatcher.setHashIterations(1024);
		customerRealm.setCredentialsMatcher(credentialsMatcher);

		/**
		 * 开启缓存管理
		 * 可以通过debug日志级别查看sql日志打印情况，或debug调试模式判断缓存是否开启成功。
		 * */
//		customerRealm.setCachingEnabled(true);  //开启全局缓存
//		customerRealm.setAuthenticationCachingEnabled(true);  //开启认证缓存
//		customerRealm.setAuthenticationCacheName("authenticationCache");
//		customerRealm.setAuthorizationCachingEnabled(true);  //开启授权缓存
//		customerRealm.setAuthorizationCacheName("authorizationCache");
//
//		customerRealm.setCacheManager(ehCacheManager());  //ehcache缓存

		return customerRealm;
	}

	// ehCache缓存管理器
	/*@Bean
	public CacheManager ehCacheManager() {
		return new EhCacheManager();
	}*/

	/**
	 * session管理器
	 */
	@Bean
	public DefaultWebSessionManager SessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionDAO(redisSessionDAO);
		return sessionManager;
	}

	/**
	 * cookie管理对象;记住我功能,rememberMe管理器
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		//rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
	}

	/**
	 * cookie对象;会话Cookie模板 ,默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid或rememberMe，自定义
	 * @return
	 */
	@Bean
	public SimpleCookie rememberMeCookie(){
		//这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		//setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
		//setcookie()的第七个参数
		//设为true后，只能通过http访问，javascript无法访问
		//防止xss读取cookie
		simpleCookie.setHttpOnly(true);
		simpleCookie.setPath("/");
		//<!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setMaxAge(2592000);
		return simpleCookie;
	}

}
