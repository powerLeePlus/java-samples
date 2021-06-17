package com.lwq.example.cloud.dubbo.gateway;

import javax.servlet.annotation.WebServlet;

import org.springframework.web.servlet.HttpServletBean;

/**
 * @author lwq
 * @date 2021/6/17 0017
 */
@WebServlet(urlPatterns = "/dsc/*")
public class DubboGatewayServlet extends HttpServletBean {
}
