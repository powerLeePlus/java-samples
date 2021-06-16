package com.lwq.example.cloud.dubbo;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lwq.example.cloud.dubbo.service.EchoService;

/**
 * @author lwq
 * @date 2021/6/16 0016
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
@RestController
public class DubboClientBootstrap {

	@DubboReference
	private EchoService echoService;

	@GetMapping("/echo/{message}")
	public String echo(@PathVariable String message) {
		return echoService.echo(message);
	}


	public static void main(String[] args) {
		SpringApplication.run(DubboClientBootstrap.class, args);
	}
}
