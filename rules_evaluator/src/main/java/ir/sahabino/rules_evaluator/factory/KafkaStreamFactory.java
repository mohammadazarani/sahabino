package ir.sahabino.rules_evaluator.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sahabino.rules_evaluator.conf.Conf;
import ir.sahabino.rules_evaluator.entity.Candle;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class KafkaStreamFactory {

    public static KafkaStreams createKafkaStream(Conf conf) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-pipe");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getKafkaBrokers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        final StreamsBuilder builder = new StreamsBuilder();
        final ObjectMapper objectMapper = new ObjectMapper();

        KStream<String, String> source = builder.stream(conf.getKafkaTopic(),
                Consumed.with(Serdes.String(), Serdes.String()));

        source.map((k, v) -> {
            try {
                Candle candle = objectMapper.readValue(v, Candle.class);
                return new KeyValue<>(k, candle);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new KeyValue<>(k, v);
        }).filter((k, v) -> ((Candle)v).getCloseTime() > 1000000 );
        final Topology topology = builder.build();
        return new KafkaStreams(topology, props);

    }
}
