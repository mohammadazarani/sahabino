package ir.sahabino.rules_evaluator.factory;

import ir.sahabino.rules_evaluator.conf.Conf;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerFactory {
    public static KafkaConsumer<String, String> createKafkaConsumer(Conf conf) {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getKafkaBrokers());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,conf.getAutoCommit() );
        props.put(ConsumerConfig.GROUP_ID_CONFIG, conf.getGroupID());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, conf.getOffsetRest());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(conf.getKafkaTopic()));
        return consumer;


    }
}
