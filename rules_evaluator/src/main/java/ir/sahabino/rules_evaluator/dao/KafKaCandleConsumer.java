package ir.sahabino.rules_evaluator.dao;

import ir.sahabino.rules_evaluator.conf.Conf;
import ir.sahabino.rules_evaluator.entity.Candle;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

public class KafKaCandleConsumer {
    private Conf config;
    private KafkaConsumer<String, String> kafkaConsumer;

    private KafKaCandleConsumer(Conf config, KafkaConsumer<String, String> kafkaConsumer) {
        this.config = config;
        this.kafkaConsumer = kafkaConsumer;
    }

    public static KafKaCandleConsumer of(Conf config, KafkaConsumer<String, String> kafkaConsumer) {
        return new KafKaCandleConsumer(config, kafkaConsumer);
    }

    public ConsumerRecords<String, String> consume() {
        return kafkaConsumer.poll(Duration.ofMillis(1000));
    }

}
