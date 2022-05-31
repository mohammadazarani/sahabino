package ir.sahabino.rules_evaluator.conf;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@Getter
public class Conf {
    private static Conf config;
    private String kafkaBrokers;
    private String kafkaTopic;
    private String groupID;
    private String autoCommit;
    private String offsetRest;


    public static Conf load() {
        if (config != null)
            return config;

        config = new Conf();
        InputStream inputStream = Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream("rules-evaluator.properties");
        Properties properties = new Properties();
        try {
            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.kafkaBrokers = properties.getProperty("rules-evaluator.kafka.brokers");
        config.kafkaTopic = properties.getProperty("rules-evaluator.kafka.topic.in");

        config.groupID = properties.getProperty("rules-evaluator.kafka.group.id");
        config.autoCommit = properties.getProperty("rules-evaluator.kafka.auto.commit");
        config.offsetRest = properties.getProperty("rules-evaluator.kafka.offset.rest");


        return config;
    }
}
