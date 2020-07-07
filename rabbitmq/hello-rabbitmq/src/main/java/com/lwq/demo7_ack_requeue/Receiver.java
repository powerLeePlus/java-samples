package com.lwq.demo7_ack_requeue;

import java.io.IOException;

import com.lwq.ConnectionUtil;
import com.rabbitmq.client.*;

/**
 * @author lwq
 * @date 2020/7/6 0006
 */
public class Receiver {

	private final static String EXCHANGE_NAME = "ack_exchange";
	private final static String QUEUE_NAME = "ack_queue";

	public static void main(String[] argv) throws Exception {

		// 获取到连接以及mq通道
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();

		// 声明交换机-持久化，非自动删除
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout", true,false,null);
		// 声明队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);

		// 绑定队列到交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

		// 定义队列的消费者
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
			                           byte[] body)
					throws IOException {
				String msg = new String(body);
				//消息序号
				int num=Integer.valueOf(msg.split(":")[1]);
				System.out.println("消费者收到消息:" + new String(body));
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(num==1) {
					System.out.println("消费者收到消息:" + new String(body)+",拒绝了这条消息");
					//requeue为true表示让消息重回队列，放入队列尾部，如果为false则会删除当前消息
					channel.basicNack(envelope.getDeliveryTag(), false, true);
				}else {
					System.out.println("消费者收到消息:" + new String(body)+",接收了这条消息");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		// 监听队列，设置为手动确认
		channel.basicConsume(QUEUE_NAME, false, consumer);

		/*
		* 运行结果如下：可以看到消息1被拒绝之后，设置了重回队列，存到了队列的尾部；然后重新投递给消费者，又被拒绝，重回队列，如此循环往复。。。。。。
		* 注意：如果设置了重回队列，因为某种原因消息无法被正确处理，就会一直重复循环重回队列——>消费拒绝——>重回队列，这样并不好，生产上不建议这样设置。
		* 如果要设置重回队列的话，要设置最大处理次数，例如为3，记录消费者处理失败的次数，当处理失败次数小于3调用Nack重回队列；如果达到了最大重试次数，则调用Ack删除消息，同时持久化该消息，后期采用定时任务或者手工处理。
		* */
	}

}
