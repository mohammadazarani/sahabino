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
import org.apache.kafka.streams.kstream.*;
import org.springframework.util.StreamUtils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class KafkaStreamFactory {

    //    Map<SimpleDateFormat, Integer>
    public static KafkaStreams createKafkaStream(Conf conf) {
        Properties props = new Properties();
        String appID = "streams-pipe14";
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, appID);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, conf.getKafkaBrokers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, CandleSerde.class);

        final StreamsBuilder builder = new StreamsBuilder();
        final ObjectMapper objectMapper = new ObjectMapper();
//
//        KStream<String, String> source = builder.stream(conf.getKafkaTopic(),
//                Consumed.with(Serdes.String(), Serdes.String()));

//        KTable<String, String> kTable = builder.table(conf.getKafkaTopic(),
//                Materialized.with(Serdes.String(), Serdes.String()));
//
        //#################
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
//        return fmt.format(date1).equals(fmt.format(date2));
//        System.out.println(source);
        /*
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
        */

        //############## filtering based on closeTime
        /*
        source.filter((k, v) -> Double.parseDouble(((Candle) v).getClose()) > 29690)
                .peek((k, v) -> System.out.println(((Candle) v).getCloseTime()))
                .foreach((k, v) -> result.add((Candle) v));
                .to("appID");
        .foreach((k, v) -> System.out.println(((Candle) v).getClose()));
        .filter((k, v) -> ((Candle) v).getCloseTime() > 1000L).to("t6");
        */

        //#####################source aggregation based on closeDateTime

        /*
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
                    Long close = Long.valueOf(((Candle) v).getClose());
                    agg.setSum(agg.getSum() + close);
                    return agg;
                }).mapValues(value -> value.getSum() / value.getCount())
                .toStream()
                .peek((k, v) -> {
                    System.out.println(v);
                }).foreach(System.out::format);
*/


        KStream<String, String> source = builder.stream(conf.getKafkaTopic(),
                Consumed.with(Serdes.String(), Serdes.String()).
                        withTimestampExtractor(new CandleCloseTimestampExtractor())
        );
        source.map((k, v) -> {
            try {
                Candle candle = objectMapper.readValue(v, Candle.class);
                return new KeyValue<>(k, candle);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return new KeyValue<>(k, v);
        })
                .groupByKey().windowedBy(SlidingWindows.ofTimeDifferenceAndGrace(Duration.ofDays(30), Duration.ofSeconds(50)))
                .aggregate(CountAndSum::new, (k, v, agg) -> {
                    agg.setCount(agg.getCount() + 1);
                    Long close = Long.valueOf(((Candle) v).getClose());
                    agg.setSum(agg.getSum() + close);
                    return agg;
                })
                    .mapValues(agg -> agg.getSum() / agg.getCount())
        .toStream().peek((k, v) -> System.out.println(v));


        final Topology topology = builder.build();

        //####
        System.out.println(topology.describe());
        return new KafkaStreams(topology, props);

    }
}
