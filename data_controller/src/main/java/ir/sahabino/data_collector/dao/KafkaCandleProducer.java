package ir.sahabino.data_collector.dao;

import ir.sahabino.data_collector.conf.KafkaConf;
import ir.sahabino.data_collector.entity.Candle;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.atomic.AtomicInteger;

public class KafkaCandleProducer {
    private KafkaConf config;
    private KafkaProducer<String, Candle> kafkaProducer;
    private AtomicInteger candlesCount = new AtomicInteger(0);

    private KafkaCandleProducer(KafkaConf config, KafkaProducer<String, Candle> kafkaProducer) {
        this.config = config;
        this.kafkaProducer = kafkaProducer;
    }

    public static KafkaCandleProducer of(KafkaConf config, KafkaProducer<String, Candle> kafkaProducer) {
        return new KafkaCandleProducer(config, kafkaProducer);
    }

    public void produce(String key,Candle candle) {
        kafkaProducer.send(new ProducerRecord<>(config.getKafkaOutputTopic(), key, candle));
        int candles = candlesCount.incrementAndGet();
        flush();

    }

    public void flush() {
        kafkaProducer.flush();
    }
}
