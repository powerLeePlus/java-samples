package org.lwq.oauth2.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lwq
 * @date 2020/7/16 0016
 */
@Controller
@RequestMapping("/oauth-client")
public class AuthAccessController {
	/** 方法一所需参
	 */
	@Value("${oauth2.client.clientId}")
	private String client_clientId;

	@Value("${oauth2.server.authorizeUrl}")
	private String server_authorizeUrl;
	@Value("${oauth2.client.redirectUrl.getAccessToken}")
	private String client_redirectUrl_getAccessToken;
	@Value("${oauth2.client.responseType.getCode}")
	private String response_type;

	/** 方法二所需参数
	 */
	@Value("${oauth2.client.clientSecret}")
	private String client_clientSecret;
	@Value("${oauth2.server.accessTokenUrl}")
	private String server_accessTokenUrl;
	@Value("${oauth2.client.redirectUrl.getUserInfo}")
	private String client_redirectUrl_getUserInfo;

	/** 方法三所需参数
	 */
	@Value("${oauth2.server.userInfoUrl}")
	private String server_userInfoUrl;

	/**
	 * 这里省略了一步，父工程README.md的详细步骤图的第一步
	 **/

	/**
	 * 一、请求授权 (Authorization Request)（对应父工程README.md的流程图）
	 * 向服务端获取code
	 * 1、拼接url然后访问，获取code
	 * 2、服务端检查成功,然后会回调到 另一个接口 /oauth-client/callbackCode
	 */
	@RequestMapping("/getCode")
	public String getCode() throws OAuthProblemException {
		String requestUrl = null;
		try {

			//配置请求参数，构建oauthd的请求。设置请求服务地址（authorizeUrl）、clientId、response_type、redirectUrl
			OAuthClientRequest accessTokenRequest = OAuthClientRequest.authorizationLocation(server_authorizeUrl)
					.setResponseType(response_type)
					.setClientId(client_clientId)
					.setRedirectURI(client_redirectUrl_getAccessToken)
					.buildQueryMessage();

			requestUrl = accessTokenRequest.getLocationUri();

		} catch (OAuthSystemException e) {
			e.printStackTrace();
		}


		System.out.println("==> 向服务端发起获取code的请求： "+ requestUrl);
		// 这是向服务端发起获取code的请求，这是客户端的一次重定向。
		return "redirect:" + requestUrl ;
	}

	/**
	 * 三、授权许可（Authorization Grant）（对应父工程README.md的流程图）
	 * 接受服务端返回的code，提交申请access token的请求
	 * 3.服务端回调,传回code值
	 * 4.根据code值，调用服务端服务,根据code获取access_token
	 * 5.拿到access_token重定向到客户端的服务  /oauth-client/getUserInfo
	 * 6.在该服务中 再调用服务端获取用户信息
	 */
	@RequestMapping("/callbackCode")
	public Object getAccessToken(HttpServletRequest request)throws OAuthProblemException {

		String code = request.getParameter("code");

		System.out.println("==> 服务端回调，获取的code："+code);

		OAuthClient oAuthClient =new OAuthClient(new URLConnectionClient());

		try {

			OAuthClientRequest accessTokenRequest = OAuthClientRequest
					.tokenLocation(server_accessTokenUrl)
					.setGrantType(GrantType.AUTHORIZATION_CODE)
					.setClientId(client_clientId)
					.setClientSecret(client_clientSecret)
					.setCode(code)
					.setRedirectURI(client_redirectUrl_getUserInfo)
					.buildQueryMessage();

			System.out.println("==> 向服务端发起获取accessToken的请求：" + accessTokenRequest.getLocationUri());

			//去服务端请求access token，并返回响应
			OAuthAccessTokenResponse oAuthResponse =oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
			//获取服务端返回过来的access token
			String accessToken = oAuthResponse.getAccessToken();
			//查看access token是否过期
			Long expiresIn =oAuthResponse.getExpiresIn();
			System.out.println("==> 客户端根据 code值 "+code +" 到服务端获取的access_token为："+accessToken+" 过期时间为："+expiresIn);

			System.out.println("==> 拿到access_token然后重定向到 客户端 [ " + client_redirectUrl_getUserInfo +" ]服务,传过去accessToken");
			// 客户端拿到token自动重定向到获取资源的URL。也可以交由server端自动重定向，取决于服务端如何实现的（是否会自动重定向）
			return"redirect:" + client_redirectUrl_getUserInfo + "?accessToken="+accessToken;

		} catch (OAuthSystemException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 五、访问令牌（Access Token）（对应父工程README.md的流程图）
	 * 接受服务端传回来的access token，由此token去请求服务端的资源（用户信息等）
	 */
	@RequestMapping("/getUserInfo")
	public String getUserInfo(String accessToken, Model model) {

		OAuthClient oAuthClient =new OAuthClient(new URLConnectionClient());
		try {
			OAuthClientRequest userInfoRequest =new OAuthBearerClientRequest(server_userInfoUrl)
					.setAccessToken(accessToken).buildQueryMessage();

			OAuthResourceResponse resourceResponse =oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
			String body = resourceResponse.getBody();
			System.out.println("==> 客户端通过accessToken："+accessToken +"  从服务端获取用户信息为："+body);

			/**
			 * 拿到第三方数据后，根据具体业务进行处理
			 * 例如：如果通过第三方用户信息做登录用，则这里拿到了用户信息，可以将用户信息进行存库，自动完成用户注册（还可以引导用户填写其他详细信息）和登录过程，然后用户可以直接访问需要登录的页面
			 *
			 * 这里模拟了登录并授权过程
			 *
			 * */
			Subject subject = SecurityUtils.getSubject();
			subject.login(new UsernamePasswordToken(body, "123")); //默认密码123
			// model.addAttribute("accessMsg", "现在可以点我进去了");
			return "index";

			// return "这是我最终想要的数据：" + body;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
