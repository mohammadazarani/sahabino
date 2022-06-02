package ir.sahabino.data_collector.conf;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class KafkaConf {
    private static KafkaConf config;
    @Getter
    private String kafkaBrokers;
    @Getter
    private String kafkaOutputTopic;

    public static KafkaConf load() {
        if (config != null)
            return config;

        config = new KafkaConf();
        InputStream inputStream = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("kafka.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            config.kafkaBrokers = properties.getProperty("collector.kafka.brokers");
            config.kafkaOutputTopic = properties.getProperty("collector.kafka.topic.out");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }
}
