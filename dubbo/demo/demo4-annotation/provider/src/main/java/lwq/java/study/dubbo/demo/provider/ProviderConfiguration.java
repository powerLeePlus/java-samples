package lwq.java.study.dubbo.demo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 服务提供配置
 *
 * @author lwq
 * @date 2019-09-11 下午 5:12
 */
@Configuration
@EnableDubbo(scanBasePackages = "lwq.java.study.dubbo.demo.provider")
@PropertySource("classpath:dubbo.properties")
@ComponentScan(basePackages = "lwq.java.study.dubbo.demo.provider")
public class ProviderConfiguration {
   /* @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        return providerConfig;
    }*/
}
