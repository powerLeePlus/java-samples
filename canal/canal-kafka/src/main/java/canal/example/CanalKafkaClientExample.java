package canal.example;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.kafka.KafkaCanalConnector;
import com.alibaba.otter.canal.protocol.FlatMessage;

/**
 * Kafka client example
 *
 * @author machengyuan @ 2018-6-12
 * @version 1.0.0
 */
public class CanalKafkaClientExample {

    protected final static Logger           LOGGER  = LoggerFactory.getLogger(CanalKafkaClientExample.class);

    private KafkaCanalConnector             connector;

    private static volatile boolean         running = false;

    private Thread                          thread  = null;

    private static final String zkserver = "172.16.20.220:2181";

    private static final String kafkaserver = "172.16.20.220:9092";

    private static final String topic = "example";

    private static final int partition = 0;

    private static final String groupId = "111111";

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {

        public void uncaughtException(Thread t, Throwable e) {
            LOGGER.error("parse events has an error", e);
        }
    };

    public CanalKafkaClientExample(String zkServers, String servers, String topic, Integer partition, String groupId){
        // 其中最后一个参数flatMessage要和canal server端配置一直
        connector = new KafkaCanalConnector(servers, topic, partition, groupId, null, true);
    }

    public static void main(String[] args) {
        // 设置日志级别
        logLevel("org", "INFO", "INFO");

        try {
            final CanalKafkaClientExample kafkaCanalClientExample = new CanalKafkaClientExample(zkserver,
                    kafkaserver, topic, partition, groupId);
            LOGGER.info("## start the kafka consumer: {}-{}", topic, groupId);
            kafkaCanalClientExample.start();
            LOGGER.info("## the canal kafka consumer is running now ......");
            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    try {
                        LOGGER.info("## stop the kafka consumer");
                        kafkaCanalClientExample.stop();
                    } catch (Throwable e) {
                        LOGGER.warn("##something goes wrong when stopping kafka consumer:", e);
                    } finally {
                        LOGGER.info("## kafka consumer is down.");
                    }
                }

            });
            while (running)
                ;
        } catch (Throwable e) {
            LOGGER.error("## Something goes wrong when starting up the kafka consumer:", e);
            System.exit(0);
        }
    }

    public void start() {
        Assert.notNull(connector, "connector is null");
        thread = new Thread(new Runnable() {

            public void run() {
                process();
            }
        });
        thread.setUncaughtExceptionHandler(handler);
        thread.start();
        running = true;
    }

    public void stop() {
        if (!running) {
            return;
        }
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    private void process() {
        while (!running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        while (running) {
            try {
                connector.connect();
                connector.subscribe();
                while (running) {
                    try {
                        /** flatMessage=false方式 */
                        /*List<Message> messages = connector.getListWithoutAck(100L, TimeUnit.MILLISECONDS); //
                        // 获取message
                        if (messages == null) {
                            continue;
                        }
                        for (Message message : messages) {
                            long batchId = message.getId();
                            int size = message.getEntries().size();
                            if (batchId == -1 || size == 0) {
                                // try {
                                // Thread.sleep(1000);
                                // } catch (InterruptedException e) {
                                // }
                            } else {
                                // printSummary(message, batchId, size);
                                // printEntry(message.getEntries());
                                LOGGER.info(message.toString());
                            }
                        }*/

                        /** flatMessage=true方式 */
                        List<FlatMessage> flatList = connector.getFlatListWithoutAck(100L, TimeUnit.MILLISECONDS);
                        if (flatList == null) {
                            continue;
                        }

                        for (FlatMessage flatMessage : flatList) {
                            LOGGER.info("获取flatMessage： " + JSONObject.toJSONString(flatMessage));
                        }

                        connector.ack(); // 提交确认
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        connector.unsubscribe();
        connector.disconnect();
    }

    // 设置日志级别
    public static void logLevel(String key, String level, String level2){
        LoggerContext loggerContext= (LoggerContext) LoggerFactory.getILoggerFactory();
        //设置全局日志级别
        ch.qos.logback.classic.Logger logger=loggerContext.getLogger("root");
        logger.setLevel(Level.toLevel(level));

        if (!StringUtils.isBlank(level2)) {
            //设置某个类日志级别-可以实现定向日志级别调整
            ch.qos.logback.classic.Logger vLogger = loggerContext.getLogger(key);
            if (vLogger!=null) {
                vLogger.setLevel(Level.toLevel(level2));
            }
        }

        List<ch.qos.logback.classic.Logger> loggerList = loggerContext.getLoggerList();
        for (ch.qos.logback.classic.Logger logger1 : loggerList){
            logger.info(logger1.getName());
        }
    }

}