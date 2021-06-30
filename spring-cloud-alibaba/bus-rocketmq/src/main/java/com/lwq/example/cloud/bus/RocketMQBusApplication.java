package com.lwq.example.cloud.bus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.bus.event.AckRemoteApplicationEvent;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author lwq
 * @date 2021/6/30 0030
 */
@EnableAutoConfiguration
@RestController
@RemoteApplicationEventScan(basePackages = "com.lwq.example.cloud.bus")
public class RocketMQBusApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(RocketMQBusApplication.class)
				.properties("server.port=0") // Random server port
				.properties("management.endpoints.web.exposure.include=*") // exposure
				// includes
				// all
				.properties("spring.cloud.bus.trace.enabled=true") // Enable trace
				.run(args);
	}

	@Autowired
	private ApplicationEventPublisher publisher;

	@Value("${spring.cloud.bus.id}")
	private String originService;

	@Value("${server.port}")
	private int localServerPort;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/bus/event/publish/user")
	public boolean publish(@RequestParam String name, @RequestParam(required = false) String destination) {
		User user = new User();
		user.setId(System.currentTimeMillis());
		user.setName(name);

		publisher.publishEvent(new UserRemoteApplicationEvent(this, originService, destination, user));

		return true;
	}

	@EventListener
	public void onEvent(UserRemoteApplicationEvent event) {
		System.out.printf("onEvent -> Server [port:%d] listeners on %s\n", localServerPort, event.getUser());
	}

	@EventListener
	public void onAckEvent(AckRemoteApplicationEvent event) throws JsonProcessingException {
		System.out.printf("onAckEvent -> Server [port:%d] listeners on %s\n", localServerPort, objectMapper.writeValueAsString(event));
	}

}
