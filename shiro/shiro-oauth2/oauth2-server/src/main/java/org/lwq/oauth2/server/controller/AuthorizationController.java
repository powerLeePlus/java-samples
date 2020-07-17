package org.lwq.oauth2.server.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.lwq.oauth2.server.pojo.User;
import org.lwq.oauth2.server.service.impl.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 二、授权许可（Authorization Grant）（对应父工程README.md的流程图）
 * @author: lwq
 * @date: 2018/5/27
 * @description: 授权控制器
 *
 * 代码的作用:
 * 1、首先通过如 http://localhost:8081/oauth-server/authorize?response_type=code&redirect_uri=http://localhost:8082/oauth-client/callbackCode&client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee
 * 2、该控制器首先检查clientId是否正确；如果错误将返回相应的错误信息
 * 3、然后判断用户是否登录了，如果没有登录首先到登录页面登录
 * 4、登录成功后生成相应的auth code即授权码，然后重定向到客户端地址，如http://localhost:8082/oauth-client/callbackCode?code=52b1832f5dff68122f4f00ae995da0ed；在重定向到的地址中会带上code参数（授权码），接着客户端可以根据授权码去换取access token。
 */
@Controller
@RequestMapping("/oauth-server")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping("/authorize")
    public Object authorize(Model model, HttpServletRequest request) throws OAuthSystemException, URISyntaxException {

        /**
         * 请求示例：http://localhost:8081/oauth-server/authorize?response_type=code&redirect_uri=http://localhost:8082/oauth-client/callbackCode&client_id=c1ebe466-1cdc-4bd3-ab69-77c3561b9dee
         *
         * 请求需要的参数
         * 1、response_type  // 授权码通过它传递给client
         * 2、redirect_uri   // 授权成功后重定向地址
         * 3、client_id      // client标识
         * */

        try {
            //构建OAuth 授权请求
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

            //根据传入的clientId 判断 客户端是否存在
            if (!authorizationService.checkClientId(oauthRequest.getClientId())) {
                //生成错误信息,告知客户端不存在
                OAuthResponse response = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                        .setErrorDescription("客户端验证失败，如错误的client_id/client_secret")
                        .buildJSONMessage();
                return new ResponseEntity(
                        response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
            }

            // 判断用户是否登录
            Subject subject = SecurityUtils.getSubject();
            //如果用户没有登录,跳转到登录页面
            if(!subject.isAuthenticated()) {
                if(!login(subject, request)) {
                    //登录失败时跳转到登陆授权页页面
                    return "/oauth2";
                }
            }
            String username = ((User) subject.getPrincipal()).getUsername();

            //生成授权码
            String authorizationCode = null;

            String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
            if(responseType.equals(ResponseType.CODE.toString())) {
                OAuthIssuerImpl oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
                authorizationCode = oAuthIssuer.authorizationCode();
                //把授权码放到缓存中
                authorizationService.addAuthCode(authorizationCode, username);
            }

            // 进行OAuth响应构建
            OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
            // 设置授权码
            builder.setCode(authorizationCode);
            // 根据客户端重定向地址
            String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
            // 构建响应
            final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();

            // 根据OAuthResponse 返回 ResponseEntity响应
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        } catch (OAuthProblemException e) {
            // 出错处理
            String redirectUri = e.getRedirectUri();
            if(OAuthUtils.isEmpty(redirectUri)) {
                // 告诉客户端没有传入redirectUri直接报错
                return new ResponseEntity("告诉客户端没有传入redirectUri直接报错！", HttpStatus.NOT_FOUND);
            }
            // 这是服务端的一次重定向
            final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
        }

    }

    private boolean login(Subject subject, HttpServletRequest request) {
        if("get".equalsIgnoreCase(request.getMethod())) {
            return false;
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return false;
        }

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
            return true;
        }catch(Exception e){

            if(e instanceof UnknownAccountException){
                request.setAttribute("msg","用户名或密码错误！");
            }

            if(e instanceof IncorrectCredentialsException){
                request.setAttribute("msg","用户名或密码错误！");
            }

            if(e instanceof LockedAccountException){
                request.setAttribute("msg","账号已被锁定,请联系管理员！");
            }
            return false;
        }
    }


}
