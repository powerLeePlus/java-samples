package com.lwq.config;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.lwq.handler.ConfirmCallBackHandlerA;
import com.lwq.handler.ReturnCallBackHandlerA;

/**
 * @author lwq
 * @date 2020/7/8 0008
 */
@Configuration
public class ProducerRabbitConfig {

	Logger logger = LoggerFactory.getLogger(ProducerRabbitConfig.class);

	@Resource
	private ConnectionFactory connectionFactory;
	@Resource
	private ConfirmCallBackHandlerA confirmCallBackHandlerA;
	@Resource
	private ReturnCallBackHandlerA returnCallBackHandlerA;

	/**
	 * 单例的RabbitTemplate<br/>
	 * 并且设置了confirm和return回调
	 * @return
	 */
	@Bean
	public RabbitTemplate singleRabbitTemplate(){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setConfirmCallback(confirmCallBackHandlerA);
		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setReturnCallback(returnCallBackHandlerA);
		return rabbitTemplate;
	}

	/**
	 * 多例的RabbitTemplate
	 * @return
	 */
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public RabbitTemplate scopeRabbitTemplate(){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMandatory(true);
		return rabbitTemplate;
	}

}
