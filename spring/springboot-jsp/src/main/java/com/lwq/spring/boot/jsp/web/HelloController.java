package com.lwq.spring.boot.jsp.web;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@Controller
public class HelloController {
 
    @RequestMapping("/hello1")
    public String hello1(Model model) {
    	// 传递数据方式一：Model addAttribute
	    model.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        return "hello";
        // return "hello.jsp";  //配置了 spring.mvc.view.suffix=.jsp ，这种是报404的
    }

	@RequestMapping("/hello2")
	public ModelAndView hello2(ModelAndView mv) {
    	// 传递数据方式二：ModelAndView.addObject
		mv.addObject("now", DateFormat.getDateTimeInstance().format(new Date()));
		mv.setViewName("hello");
		return mv;
	}

	@RequestMapping("/hello3")
	public String hello3(ModelMap modelMap) {
		// 传递数据方式三：ModelAndView.addAttribute
		modelMap.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
		return "hello";
	}

	@RequestMapping("/test")
	public String test(Model m) {
		m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
		return "test1";
	}

	@RequestMapping("/forward/test")
	public String forward(Model m) {
		m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));

		// 注意：return "forward:/test2" 和 return "forward:test2" 是有区别的
		return "forward:/test2";  // 要存在test2这个Controller，否则报404
		// return "forward:/test2.jsp";  // 这种当然也报404
		// return "test2";
	}

	@RequestMapping("/redirect/test")
	public String redirect(Model model, RedirectAttributesModelMap redirectAttributesModelMap) throws UnsupportedEncodingException {
		model.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));  // redirect: 数据传递不出去

		// redirect传递数据方式一：redirectAttributesModelMap 最后也是拼接成url params方式，
		redirectAttributesModelMap.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));

		// 注意：return "redirect:/test3" 和 return "redirect:test3" 是有区别的
		return "redirect:/test3";  // 要存在test3这个Controller，否则报404

		// redirect传递数据方式二：自己拼接url params方式，
		/*String encode = URLEncoder.encode(DateFormat.getDateTimeInstance().format(new Date()), "UTF-8");
		String r = "redirect:/test3?now=" + encode;
		return r;*/

	}

	@RequestMapping("/test2")
	public String test2(Model m) {
    	// 通过/forward/test转发过来的请求数据 now 也可以带过来
		return "test2";
	}

	@RequestMapping("/test3")
	public String test3(Model m) {
		// 通过/redirect/test转发过来的请求数据now 没有带过来，可以通过
		return "test3";
	}
}