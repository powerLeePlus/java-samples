package com.lwq.example;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;

/** 自定义默认http（URL）限流异常处理
 * @author lwq
 * @date 2021/5/31 0031
 */
@Component
public class CustomUrlBlockHandler implements BlockExceptionHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws Exception {
		String msg = null;
		if (ex instanceof FlowException) {
			msg = "限流了";
		} else if (ex instanceof DegradeException) {
			msg = "降级了";
		} else if (ex instanceof ParamFlowException) {
			msg = "热点参数限流";
		} else if (ex instanceof SystemBlockException) {
			msg = "系统规则（负载/...不满足要求）";
		} else if (ex instanceof AuthorityException) {
			msg = "授权规则不通过";
		}
		// http状态码
		response.setStatus(500);
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "application/json;charset=utf-8");
		response.setContentType("application/json;charset=utf-8");
		try(PrintWriter writer = response.getWriter()) {
			writer.write(msg);
			writer.flush();
		}
	}
}
