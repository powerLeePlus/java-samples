package com.lwq.example;

import java.util.List;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * @author lwq
 * @date 2021/5/31 0031
 */
public class JsonFlowRuleListConverter implements Converter<String, List<FlowRule>> {

	/**
	 * 自定义限流规则数据源解析转换
	 */
	@Override
	public List<FlowRule> convert(String source) {
		return JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
		});
	}

}
