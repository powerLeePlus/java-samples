package com.example.demo.consumer;

import static com.example.demo.Constants.CONSUMER_TOPIC;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.example.demo.Constants;

/**
 * @author lwq
 * @date 2020/3/31 0031
 */
//@Slf4j
public class Consumer {

    private static KafkaConsumer<String, String> consumer;

    /* 初始化消费者*/
    static {
        Properties configs = initConfig();
        consumer = new KafkaConsumer<>(configs);
        consumer.subscribe(Arrays.asList(CONSUMER_TOPIC, "aaaaaa"));
        //consumer.subscribe(Arrays.asList(CONSUMER_TOPIC, "example"));
    }

    /* 初始化配置*/
    private static Properties initConfig() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.16.20.220:9092");
        //props.put("bootstrap.servers", Constants.ADDRESS_COLLECTION);
        props.put("group.id", "test");
        //props.put("group.id", Constants.CONSUMER_GROUP_ID);
        props.put("enable.auto.commit", Constants.CONSUMER_ENABLE_AUTO_COMMIT);
        props.put("auto.commit.interval.ms", Constants.CONSUMER_AUTO_COMMIT_INTERVAL_MS);
        props.put("session.timeout.ms", Constants.CONSUMER_SESSION_TIMEOUT_MS);
        props.put("max.poll.records", Constants.CONSUMER_MAX_POLL_RECORDS);
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        return props;
    }

    public static void main(String[] args) {
        // 设置日志级别
        logLevel("org", "INFO", "INFO");

        // 1、可以用这个命令去 通过时间戳获取offset
        // 新版本的kafka提供了更简单的方法（应该是kafka2.0+)，如果没有就自己装个客户端，访问旧的Kafka（比如0.10.0）都是可用的。
        // kafka-run-class.bat kafka.tools.GetOffsetShell --broker-list broker1:9092,broker2:9092 -topic topicName -time timestamp
        // 上述的-time，为毫秒值，-time=-1表示latest，-2表示earliest。结果为：
        /* 如：
         * kafka-run-class.bat kafka.tools.GetOffsetShell --broker-list localhost:9092 -topic topicDemo -time 1586868428898
         * 将得到：topicDemo:0:380
         * 该offset值是所写时间戳之后的第一条数据。
         *
         */
        // 2、也可以用下面的API 通过时间戳获取offset
        HashMap<TopicPartition, Long> map = new HashMap<>();
        //TopicPartition topicPartition = new TopicPartition("kafkacluster1test2", 0);
        //TopicPartition topicPartition = new TopicPartition(CONSUMER_TOPIC, 0);
        /*map.put(topicPartition, 1586870011273L);
        Map<TopicPartition, OffsetAndTimestamp> offsetAndTimestampMap = consumer.offsetsForTimes(map);
        OffsetAndTimestamp offsetAndTimestamp = offsetAndTimestampMap.get(topicPartition);
        System.out.println("offsetAndTimestampMap: " + offsetAndTimestampMap);
        System.out.println("offset: " + offsetAndTimestamp.offset());*/
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Constants.CONSUMER_POLL_TIME_OUT);
            records.forEach(record -> {
                //System.out.println(String.format("收到: key ====== %s, value ====== %s, topic ====== %s", record.key(),
                //        record.value(), record.topic()));
                System.out.println("主题：" + record.topic() + "  offset: " + record.offset());
                System.out.println("收到: key ====== " + record.key() + "，value ====== " + record.value() + "，topic " +
                                "====== " + record.topic() + "，partition " + "====== " + record.partition());
            });
        }
    }

    // 设置日志级别
    public static void logLevel(String key, String level, String level2){
        LoggerContext loggerContext= (LoggerContext) LoggerFactory.getILoggerFactory();
        //设置全局日志级别
        ch.qos.logback.classic.Logger logger=loggerContext.getLogger("root");
        logger.setLevel(Level.toLevel(level));

        if (!StringUtils.isEmpty(level2)) {
            //设置某个类日志级别-可以实现定向日志级别调整
            ch.qos.logback.classic.Logger vLogger = loggerContext.getLogger(key);
            if (vLogger!=null) {
                vLogger.setLevel(Level.toLevel(level2));
            }
        }

        List<Logger> loggerList = loggerContext.getLoggerList();
        for (ch.qos.logback.classic.Logger logger1 : loggerList){
            logger.info(logger1.getName());
        }
    }
}
