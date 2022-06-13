package lwq.java.study.dubbo.demo.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 服务消费方配置
 *
 * @author lwq
 * @date 2019-09-11 下午 5:19
 */
@Configuration
@EnableDubbo(scanBasePackages = "lwq.java.study.dubbo.demo.consumer")
@PropertySource("classpath:dubbo.properties")
@ComponentScan(basePackages = "lwq.java.study.dubbo.demo.consumer")
public class ConsumerConfiguration {
}
