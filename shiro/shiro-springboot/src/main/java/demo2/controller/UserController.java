package demo2.controller;

import demo2.pojo.User;
import demo2.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lwq
 * @date 2020/7/9 0009
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 用来处理身份认证
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("login")
	public String login(String username,String password, boolean rememberMe){
		//获取主体对象
		Subject subject = SecurityUtils.getSubject();
		try {
			// 加记住我功能
			subject.login(new UsernamePasswordToken(username,password, rememberMe));
			return "redirect:/index";
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			System.out.println("用户名错误!");
		}catch (IncorrectCredentialsException e){
			e.printStackTrace();
			System.out.println("密码错误!");
		}
		return "/login";
	}

	@RequestMapping("logout")
	public String logout(){
		//获取主体对象
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "/login";
	}

	@RequestMapping("register")
	public String register(User user){
		userService.register(user);
		return "/login";
	}


	@RequestMapping("list")
	public String list(){
		//获取主体对象
		Subject subject = SecurityUtils.getSubject();

		try {
			// 授权方式1：编码
			// 注意：该方式使ShiroConfig中配置的shiroFilterFactoryBean.setUnauthorizedUrl不生效
			subject.checkRole("admin");
			return "/users";
		} catch (UnauthorizedException e) {
			e.printStackTrace();
			System.out.println("没有admin权限");
			return "unauthorized";
		}

	}

	// 授权方式2：注解
	/*@RequiresRoles({"admin","user"})  // 注意：该注解使ShiroConfig中配置的shiroFilterFactoryBean.setUnauthorizedUrl不生效
	@RequiresPermissions("user:find:01")
	@RequestMapping("list")
	public String list2(){
		// 可以使用全局异常处理来处理UnauthorizedException
		return "/users";
	}*/

	// 授权方式3：ShiroConfig setFilterChainDefinitionMap + 标签,如： map.put("/index", "roles") + <shiro:hasRole name="admin">
}
