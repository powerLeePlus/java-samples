package com.example.demo.producer;

import static com.example.demo.Constants.ADDRESS_COLLECTION;
import static com.example.demo.Constants.PRODUCER_TOPIC;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author lwq
 * @date 2020/3/31 0031
 */
//@Slf4j
public class Producer {

    private static KafkaProducer<String, String> producer;

    /* 初始化生产者*/
    static {
        Properties configs = initConfig();
        producer = new KafkaProducer<>(configs);
    }

    /* 初始化配置*/
    private static Properties initConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", ADDRESS_COLLECTION);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        return props;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ProducerRecord<String, String> record = null;
        String msg = "{\"data\":[{\"id\":\"160771\",\"user_id\":\"100005465\",\"type\":\"-1\"," +
                "\"integral_num\":\"-10\",\"all_integral_num\":\"3617\",\"task_id\":\"0\",\"task_type\":\"0\",\"commodity_id\":\"189\",\"commodity_source\":\"0\",\"remark\":\"幸运抽奖10积分\",\"sender_email\":null,\"sender\":\"0\",\"mobile\":null,\"create_date\":\"2020-04-17\",\"create_time\":\"2020-04-17 16:41:04\",\"var1\":null,\"var2\":null,\"var3\":null,\"act_id\":\"0\",\"get_id\":\"0\"}],\"database\":\"integral\",\"es\":1587112864000,\"id\":7834,\"isDdl\":false,\"mysqlType\":{\"id\":\"bigint(11)\",\"user_id\":\"bigint(11)\",\"type\":\"int(2)\",\"integral_num\":\"int(4)\",\"all_integral_num\":\"int(6)\",\"task_id\":\"int(2)\",\"task_type\":\"int(2)\",\"commodity_id\":\"int(2)\",\"commodity_source\":\"int(1)\",\"remark\":\"varchar(50)\",\"sender_email\":\"varchar(50)\",\"sender\":\"bigint(11)\",\"mobile\":\"varchar(11)\",\"create_date\":\"date\",\"create_time\":\"datetime\",\"var1\":\"varchar(20)\",\"var2\":\"varchar(20)\",\"var3\":\"varchar(20)\",\"act_id\":\"int(8)\",\"get_id\":\"int(8)\"},\"old\":null,\"pkNames\":[\"id\"],\"sql\":\"\",\"sqlType\":{\"id\":-5,\"user_id\":-5,\"type\":4,\"integral_num\":4,\"all_integral_num\":4,\"task_id\":4,\"task_type\":4,\"commodity_id\":4,\"commodity_source\":4,\"remark\":12,\"sender_email\":12,\"sender\":-5,\"mobile\":12,\"create_date\":91,\"create_time\":93,\"var1\":12,\"var2\":12,\"var3\":12,\"act_id\":4,\"get_id\":4},\"table\":\"user_integral_change\",\"ts\":1587112864103,\"type\":\"INSERT\"}";
        for (int i = 0; i < 10; i++) {
            //record = new ProducerRecord<>("example", msg);
            record = new ProducerRecord<>(PRODUCER_TOPIC, msg);
            //record = new ProducerRecord<>(PRODUCER_TOPIC, "value" + i);
            // 1、发消息，异步
            /*producer.send(record, new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(null != e) {
                        System.out.printf("send error" + e.getMessage());
                        //log.info("send error" + e.getMessage());
                    }else {
                        System.out.println(String.format("offset: %s , partition: %s", recordMetadata.offset(),
                                recordMetadata.partition()));
                    }
                }
            });*/

            // 2、发消息，同步
            RecordMetadata recordMetadata = producer.send(record).get(10, TimeUnit.SECONDS);
            System.out.println(System.currentTimeMillis() + ":  " + recordMetadata.toString() + ":  " + recordMetadata.offset());

            Thread.sleep(2000);
        }
        producer.close();
    }
}
