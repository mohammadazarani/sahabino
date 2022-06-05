package ir.sahabino.rules_evaluator.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sahabino.rules_evaluator.entity.Candle;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CandleSerializer implements Serializer<Candle> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, Candle candle) {
        try {
            return new ObjectMapper().writer().writeValueAsBytes(candle);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }
}
