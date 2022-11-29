package com.lwq.spring.aop.annotation.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lwq.spring.aop.annotation.dto.GrpSearceDto;
import com.lwq.spring.aop.annotation.service.HelloService;


@RequestMapping("/aop")
@RestController
public class HelloController {

	@Autowired
	private HelloService helloService;

	// @PermissionOrgAccessor
	@GetMapping("/test")
	public String test() {
	/**
	 * @PermissionOrg注解用在Controller方法上也行。
	 * 只是需要通过注解@RequestParam(required = false)表明该参数可为null(可不传)，否则会报错
	 */
	// public String test(@PermissionOrg @RequestParam(required = false) List<DepNameDto> depNameDtos) {
		// 自动注入不依赖其它参数 start
		// helloService.depOperation(null);
		// helloService.grpOperation("小组长集合", null);
		// helloService.memberOperation(null, "战士们冲啊");
		// helloService.oneDepOperation(null);
		// helloService.oneDepOperation2(null, "战士冲啊");
		// 自动注入不依赖其它参数 end

		// 自动注入需要依赖其它参数 start
		// helloService.depOperation(null);
		// helloService.grpOperation2("小组长集合", null, 2);
		// helloService.memberOperation2(3, null, "战士冲啊");
		// 自动注入不依赖其它参数 end

		// 自动注入，依赖其他参数，参数上注解@PermissionOrg，但是要自动注入赋值的是该参数类上的某个field start
		GrpSearceDto grpSearceDto = new GrpSearceDto();
		grpSearceDto.setName("哈哈");
		helloService.grpOperation3("小组长集合", 2, grpSearceDto);
		// 自动注入，依赖其他参数，参数上注解@PermissionOrg，但是要自动注入赋值的是该参数类上的某个field end
		return "ok";
	}
}