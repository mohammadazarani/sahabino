package ir.sahabino.data_collector.factory;

import ir.sahabino.data_collector.conf.Conf;
import ir.sahabino.data_collector.entity.Candle;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerFactory {

    public static KafkaProducer<String, Candle> createKafkaProducer(Conf conf){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getKafkaBrokers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Candle.class.getName());
        return new KafkaProducer<>(properties);
    }
}
