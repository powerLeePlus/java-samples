package demo1.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lwq
 * @date 2020/7/9 0009
 */
@Controller
@RequestMapping("user")
public class UserController {
	/**
	 * 用来处理身份认证
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("login")
	public String login(String username,String password){
		//获取主体对象
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(new UsernamePasswordToken(username,password));
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
	/*@RequiresRoles("admin")  // 注意：该注解使ShiroConfig中配置的shiroFilterFactoryBean.setUnauthorizedUrl不生效
	@RequestMapping("list")
	public String list(){
		// 可以使用全局异常处理来处理UnauthorizedException
		return "/users";
	}*/

	// 授权方式3：ShiroConfig setFilterChainDefinitionMap + 标签,如： map.put("/index", "roles") + <shiro:hasRole name="admin">
}
