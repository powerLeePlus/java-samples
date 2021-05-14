package com.lwq.rabbitmq.delayqueue;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lwq
 * @date 2020/7/8 0008
 */
@Configuration
public class RabbitConfig {

	Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

	@Resource
	private ConnectionFactory connectionFactory;

	/**
	 * 单例的RabbitTemplate<br/>
	 * @return
	 */
	@Bean
	public RabbitTemplate singleRabbitTemplate(){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		return rabbitTemplate;
	}

	//延时交换机
	public static final String DELAY_EXCHANGE = "delay-exchange";
	//延时路由
	public static final String DELAY_ROUTING_KEY = "delay-routing-key";
	//延时队列
	public static final String DELAY_QUEUE = "delay-queue";

	//死信交换机
	public static final String DELAY_AFTER_EXCHANGE = "delayafter-exchange";
	//死信路由
	public static final String DELAY_AFTER_ROUTING_KEY = "delayafter-routing-key";
	//死信队列
	public static final String DELAY_AFTER_QUEUE = "delayafter-queue";

	@Bean("delayAdmin")
	public RabbitAdmin rabbitAdmin() {
		RabbitAdmin admin = new RabbitAdmin(connectionFactory);

		/**
		 * 定义延时队列（投递到该队列的消息要设置TTL）
		 */
		DirectExchange delayExchange = new DirectExchange(DELAY_EXCHANGE);
		admin.declareExchange(delayExchange);
		Map args = new HashMap();
		args.put("x-dead-letter-exchange", DELAY_AFTER_EXCHANGE);
		args.put("x-dead-letter-routing-key", DELAY_AFTER_ROUTING_KEY);
		/**
		 * 可以给队列设置ttl，
		 * 也可以给每条message设置ttl
		 */
		// args.put("x-message-ttl", ParamUtil.pushDingDelayMin * 1000 * 60);
		Queue delayQueue = QueueBuilder.durable(DELAY_QUEUE).withArguments(args).build();
		Binding delayBinding = BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_ROUTING_KEY);
		admin.declareQueue(delayQueue);
		admin.declareBinding(delayBinding);

		/**
		 * 定义延时队列消息到期转投的队列（死信队列）
		 */
		DirectExchange normalExchange = new DirectExchange(DELAY_AFTER_EXCHANGE);
		admin.declareExchange(normalExchange);
		Queue normalQueue = new Queue(DELAY_AFTER_QUEUE);
		Binding normalBinding = BindingBuilder.bind(normalQueue).to(normalExchange).with(DELAY_AFTER_ROUTING_KEY);
		admin.declareQueue(normalQueue);
		admin.declareBinding(normalBinding);

		return admin;
	}

	/**
	 * 消费延时队列转投的死信队列
	 */
	@Bean
	public DirectMessageListenerContainer pushDelayAfterListener(@Autowired DelayAfterListener DelayAfterListener) {
		DirectMessageListenerContainer listenerContainerEnter = new DirectMessageListenerContainer(connectionFactory);
		listenerContainerEnter.setMessageListener(DelayAfterListener);
		listenerContainerEnter.setQueueNames(DELAY_AFTER_QUEUE);
		listenerContainerEnter.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		listenerContainerEnter.setPrefetchCount(1);
		return listenerContainerEnter;
	}


}
