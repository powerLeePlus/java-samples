package demo.lwq.spring.boot.starter.customizer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置
 * @author lwq
 * @date 2022/7/29 0029
 * @since
 */
@Configuration
public class CustomizerConfiguration {

	@Bean
	public AddService addService() {
		System.out.println("create addService");
		return new AddServiceImpl();
	}

	/**
	 * 如果配置了minusService.supportnegative=true,就实例化{@link MinusServiceSupportNegativeImpl}
	 */
	@Bean
	@ConditionalOnProperty(prefix = "minusService", name = "supportnegative", havingValue = "true")
	public MinusService supportMinusService() {
		System.out.println("create minusService support minus");
		return new MinusServiceSupportNegativeImpl();
	}

	/**
	 * 如果没有配置minusService.supportnegative=true,就不会实例化{@link MinusServiceSupportNegativeImpl}，
	 * 这里的条件是如果没有{@link MinusService}类型的bean，就在此实例化一个
	 */
	@Bean
	@ConditionalOnMissingBean(MinusService.class)
	public MinusService getNotSupportMinusService() {
		System.out.println("create minusService not support minus");
		return new MinusServiceNotSupportNegativeImpl();
	}
}
