package org.lwq.oauth2.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.lwq.oauth2.server.service.impl.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 四、访问令牌（Access Token）（对应父工程README.md的流程图）
 * 获取oauth2授权后，用授权码获取要访问资源的令牌
 * @author lwq
 * @date 2020/7/16 0016
 * * 代码描述：
 * * 1、首先通过如http://localhost:9090/accessToken，POST提交如下数据：client_id= c1ebe466-1cdc-4bd3-ab69-77c3561b9dee& client_secret= d8346ea2-6017-43ed-ad68-19c0f971738b&grant_type=authorization_code&code=828beda907066d058584f37bcfd597b6&redirect_uri=http://localhost:9080/oauth-client/oauth2-login访问。
 * * 2、该控制器会验证client_id、client_secret、auth code的正确性，如果错误会返回相应的错误；
 * * 3、如果验证通过会生成并返回相应的访问令牌access token。
 *
 */
@RestController
@RequestMapping("/oauth-server")
public class AccessTokenController {

	@Autowired
	private AuthorizationService authorizationService;

	@RequestMapping("/accessToken")
	public HttpEntity token(HttpServletRequest request) throws OAuthSystemException {

		/**
		 * 要Post请求
		 * 请求示例：http://localhost:8081/oauth-server/accessToken?client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee&client_secret=d8346ea2-6017-43ed-ad68-19c0f971738b&code=b55cf10ab488f3f329ecc2e5c5ad44c8&grant_type=authorization_code&redirect_uri=http://localhost:8082/oauth-client/oauth2-login
		 *
		 * 请求需要的参数
		 * 1、client_id      // client标识
		 * 2、client_secret  // client密钥
		 * 3、code   // 自定义的授权码key
		 * 4、grant_type  //获取accessToken的类型
		 * 5、redirect_uri  // client回调重定向
		 * */

		try {
			// 构建Oauth请求
			OAuthTokenRequest oAuthTokenRequest = new OAuthTokenRequest(request);

			//检查提交的客户端id是否正确
			if(!authorizationService.checkClientId(oAuthTokenRequest.getClientId())) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
						.setErrorDescription("客户端验证失败，client_id错误！")
						.buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			// 检查客户端安全Key是否正确
			if(!authorizationService.checkClientSecret(oAuthTokenRequest.getClientSecret())){
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
						.setErrorDescription("客户端验证失败，client_secret错误！")
						.buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}

			String authCode = oAuthTokenRequest.getParam(OAuth.OAUTH_CODE);

			// 检查验证类型，此处只检查AUTHORIZATION类型，其他的还有PASSWORD或者REFRESH_TOKEN
			if(oAuthTokenRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())){
				if(!authorizationService.checkAuthCode(authCode)){
					OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("auth_code错误！")
							.buildJSONMessage();
					return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
				}
			}

			//生成Access Token
			OAuthIssuer issuer = new OAuthIssuerImpl(new MD5Generator());
			final String accessToken  = issuer.accessToken();
			authorizationService.addAccessToken(accessToken, authorizationService.getUsernameByAuthCode(authCode));

			// 生成OAuth响应
			OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(accessToken).setExpiresIn(String.valueOf(authorizationService.getExpireIn()))
					.buildJSONMessage();

			return new ResponseEntity(response.getBody(),HttpStatus.valueOf(response.getResponseStatus()));
		} catch(OAuthProblemException e) {
			OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e).buildBodyMessage();
			return new ResponseEntity(res.getBody(),HttpStatus.valueOf(res.getResponseStatus()));
		}
	}

}