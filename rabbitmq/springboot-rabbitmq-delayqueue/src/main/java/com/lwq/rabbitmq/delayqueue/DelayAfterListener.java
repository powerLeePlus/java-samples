package com.lwq.rabbitmq.delayqueue;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
 * 死信队列消费
 * @author lwq
 * @date 2021/05/13
 */
@Component
public class DelayAfterListener implements ChannelAwareMessageListener {
    private final static Logger logger = LoggerFactory.getLogger(DelayAfterListener.class);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        String value = new String(message.getBody());
        logger.info("收到了延迟后的信息：{} ， 当前时间：{}", value, LocalDateTime.now());

    }
}
