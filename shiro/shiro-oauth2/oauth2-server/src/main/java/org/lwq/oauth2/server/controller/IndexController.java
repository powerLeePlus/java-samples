package org.lwq.oauth2.server.controller;

import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lwq
 * @date 2020/7/9 0009
 */
@Controller
public class IndexController {

	@RequestMapping("index")
	public String index(Model model) {
		System.out.println("跳转到主页");
		return "index"; // 要带着后缀就要用redirect
	}

	@RequestMapping("hello")
	public String hello(Model m) {
		m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
		return "hello";
	}

}
