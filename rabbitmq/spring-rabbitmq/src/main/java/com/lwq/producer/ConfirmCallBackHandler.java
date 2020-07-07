package com.lwq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 做消息可靠性投递
 * @author lwq
 * @date 2020/7/7 0007
 */
public class ConfirmCallBackHandler implements RabbitTemplate.ConfirmCallback {
	Logger logger = LoggerFactory.getLogger(ConfirmCallBackHandler.class);

	/**
	 * confirm确认回调处理：ack为true表示投递成功，为false表示投递失败（可以重新投递或者记录数据库），
	 * CorrelationData参数为发消息时指定的一个唯一ID，可以与发送的消息做关联，表示该消息已经成功发到了RabbitMQ上了，用于做消息可靠性投递
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		//根据回调的correlationDataId判断发送的哪条消息投递成功，哪条没有成功
		String correlationDataId =null;
		if(correlationData!=null){
			correlationDataId = correlationData.getId();
		}
		if(ack){
			logger.info("CONFIRM机制:消息投递成功:correlationId:{}",correlationDataId);
		}else{
			//TODO 消息投递失败,重新投递
			logger.warn("CONFIRM机制:消息投递失败:correlationId:{},cause:{}",correlationDataId,cause);
		}
	}
}
