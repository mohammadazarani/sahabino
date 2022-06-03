package ir.sahabino.rules_evaluator.factory;

import ir.sahabino.rules_evaluator.conf.Conf;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Collections;
import java.util.Properties;

public class KafkaStreamFactory {

    public static KStream<String, String> createKafkaStream(Conf conf) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getKafkaBrokers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> source = builder.stream(conf.getKafkaTopic());
//        source.filter(ca)
//        source.filter();
//        KafkaStreams k  = new KafkaStreams(conf.getKafkaTopic(), props);

        return source;
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

//        consumer.subscribe(Collections.singletonList(conf.getKafkaTopic()));
//        return consumer;


    }
}
