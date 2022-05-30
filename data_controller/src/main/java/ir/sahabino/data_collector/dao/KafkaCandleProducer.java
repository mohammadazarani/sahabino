package ir.sahabino.data_collector.dao;

import ir.sahabino.data_collector.conf.Conf;
import ir.sahabino.data_collector.entity.Candle;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.atomic.AtomicInteger;

public class KafkaCandleProducer {
    private Conf config;
    private KafkaProducer<String, Candle> kafkaProducer;
    private AtomicInteger candlesCount = new AtomicInteger(0);

    private KafkaCandleProducer(Conf config, KafkaProducer<String, Candle> kafkaProducer) {
        this.config = config;
        this.kafkaProducer = kafkaProducer;
    }

    public static KafkaCandleProducer of(Conf config, KafkaProducer<String, Candle> kafkaProducer){
        return new KafkaCandleProducer(config, kafkaProducer);
    }

    public void produce(Candle candle) {
        kafkaProducer.send(new ProducerRecord<>(
                config.getKafkaOutputTopic(),
                candle
          ));
        int candles = candlesCount.incrementAndGet();
        System.err.println(candles);
        if (candles % 2 == 0){
            flush();
        }
    }

    public void flush(){
        kafkaProducer.flush();
    }
}
