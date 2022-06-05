package ir.sahabino.rules_evaluator.deserializer;

import ir.sahabino.rules_evaluator.entity.Candle;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public final class CandleSerde extends Serdes {
        private CandleSerde() {}

        public static Serde<Candle> candleSerde() {
            CandleDeserializer deserializer = new CandleDeserializer();
            CandleSerializer serializer = new CandleSerializer();
            return Serdes.serdeFrom(serializer, deserializer);
        }

//        public static Serde<GenreCount> GenreCount() {
//            JsonSerializer<GenreCount> serializer = new JsonSerializer<>();
//            JsonDeserializer<GenreCount> deserializer = new JsonDeserializer<>(GenreCount.class);
//            return Serdes.serdeFrom(serializer, deserializer);
//        }
    }

