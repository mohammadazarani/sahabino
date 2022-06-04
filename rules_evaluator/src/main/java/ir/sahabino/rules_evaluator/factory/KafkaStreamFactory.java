package ir.sahabino.rules_evaluator.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sahabino.rules_evaluator.conf.Conf;
import ir.sahabino.rules_evaluator.deserializer.CandleDeserializer;
import ir.sahabino.rules_evaluator.deserializer.CandleSerde;
import ir.sahabino.rules_evaluator.entity.Candle;
import ir.sahabino.rules_evaluator.entity.CountAndSum;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.internals.StreamsConfigUtils;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.util.StreamUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class KafkaStreamFactory {

    //    Map<SimpleDateFormat, Integer>
    public static KafkaStreams createKafkaStream(Conf conf, List<Candle> result) {
        Properties props = new Properties();
        String appID = "streams-pipe12";
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, appID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getKafkaBrokers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CandleSerde.class);

        final StreamsBuilder builder = new StreamsBuilder();
        final ObjectMapper objectMapper = new ObjectMapper();

        KStream<String, String> source = builder.stream(conf.getKafkaTopic(),
                Consumed.with(Serdes.String(), Serdes.String()));

        //####
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
//        return fmt.format(date1).equals(fmt.format(date2));
        System.out.println(source);
        source.map((k, v) -> {
            try {
                Candle candle = objectMapper.readValue(v, Candle.class);
                return new KeyValue<>(k, candle);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new KeyValue<>(k, v);
        })
                .groupBy((k, v) -> ((Candle) v).getCandleCloseData())
                .aggregate(CountAndSum::new, (k, v, agg) -> {
                    agg.setCount(agg.getCount() + 1);
                    Long close = Long.valueOf(((Candle)v).getClose());
                    agg.setSum(agg.getSum() + close);
                    return agg;
                }).mapValues(value -> value.getSum() / value.getCount())
                .toStream().foreach(System.out::format);
//                .reduce((c1, c2) -> )
//                .aggregate()
//                .filter((k
//                , v) -> Double.parseDouble(((Candle) v).getClose()) > 29690)
//                .peek((k, v) -> System.out.println(((Candle) v).getCloseTime()))
//                .foreach((k, v) -> result.add((Candle) v));
//                .to("appID");
//        .foreach((k, v) -> System.out.println(((Candle) v).getClose()));
//        .filter((k, v) -> ((Candle)v).getCloseTime() > 1000L ).to("t6");
        final Topology topology = builder.build();

        //####
        System.out.println(topology.describe());
        return new KafkaStreams(topology, props);

    }
}
