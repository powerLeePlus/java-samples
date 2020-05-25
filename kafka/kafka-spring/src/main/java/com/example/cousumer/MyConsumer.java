package com.example.cousumer;

import static com.example.config.KafkaConstants.ERROR_HANDLER;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.example.config.KafkaConstants;
import com.example.handler.HandlerThreadFactory;

/**
 * @author lwq
 * @date 2020/4/24 0024
 */
@Component
public class MyConsumer {

	/*@KafkaListener(id = "hello", topics = KafkaConstants.DEFAULT_TOPIC)
	public void consum(ConsumerRecord<?, ?> records){
		System.out.println("收到：" + records.value());
	}*/

	@KafkaListener(id = "test", topics = {KafkaConstants.DEFAULT_TOPIC,"user_integral_repair"}, errorHandler =
			ERROR_HANDLER)
	public void consum2(ConsumerRecord<String, String> record, Acknowledgment ack){
		try {
				//ExecutorService executorService = Executors.newFixedThreadPool(3);

				ExecutorService executorService = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS,
						new LinkedBlockingQueue<Runnable>(), new HandlerThreadFactory());

				executorService.execute(new DataProcess(record));

				executorService.shutdown();

			ack.acknowledge();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private class DataProcess implements Runnable {

		private ConsumerRecord<String, String> consumerRecord;

		public DataProcess(ConsumerRecord<String, String> consumerRecord) {
			super();
			this.consumerRecord = consumerRecord;
		}

		@Override
		public void run() {

			String flatKey = consumerRecord.key();
			long offset = consumerRecord.offset();// 偏移量;

			String topic = consumerRecord.topic();
			System.out.println("收到消息：");
			System.out.println(topic);

			System.out.println("flatKey: " + flatKey);
			System.out.println("offset: " + offset);

			String value = consumerRecord.value();
			System.out.println("value: " + value);

		}
	}
}
