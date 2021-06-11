package com.lwq.example;

import static com.lwq.example.RocketmqConsumerApplication.MySink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author lwq
 * @date 2021/6/9 0009
 */
@SpringBootApplication
@EnableBinding({MySink.class})
public class RocketmqConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketmqConsumerApplication.class, args);
	}

	@Bean
	public ConsumerCustomRunner customRunner() {
		return new ConsumerCustomRunner();
	}

	public interface MySink {

		@Input("input1")
		SubscribableChannel input1();

		@Input("input2")
		SubscribableChannel input2();

		@Input("input3")
		SubscribableChannel input3();

		@Input("input4")
		SubscribableChannel input4();

		@Input("input5")
		PollableMessageSource input5();
	}

	public static class ConsumerCustomRunner implements CommandLineRunner {

		@Autowired
		private MySink mySink;

		@Override
		public void run(String... args) throws Exception {
			while (true) {
				mySink.input5().poll(message -> {
					String payload = (String) message.getPayload();
					if (payload.contains("0")) {
						throw new IllegalArgumentException("11111111111");
					}
					System.out.println("input5 pull message:" + payload);
				}, new ParameterizedTypeReference<String>() {
				});
				Thread.sleep(5_00);
			}
		}
	}
}
