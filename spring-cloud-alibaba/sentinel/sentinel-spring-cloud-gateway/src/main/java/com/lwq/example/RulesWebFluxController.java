package com.lwq.example;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import reactor.core.publisher.Mono;

/**
 * @author lwq
 * @date 2021/5/28 0028
 */
@RestController
public class RulesWebFluxController {

	@GetMapping("/api")
	public Mono<Set<ApiDefinition>> apiRules() {
		return Mono.just(GatewayApiDefinitionManager.getApiDefinitions());
	}

	@GetMapping("/gateway")
	public Mono<Set<GatewayFlowRule>> apiGatewayRules() {
		return Mono.just(GatewayRuleManager.getRules());
	}

	@GetMapping("/flow")
	public Mono<List<FlowRule>> apiFlowRules() {
		return Mono.just(FlowRuleManager.getRules());
	}

}
