package ir.sahabino.rules_evaluator.deserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sahabino.rules_evaluator.entity.Candle;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CandleDeserialize implements Deserializer<Candle> {
//    protected Deserializer<List<Candle>> kafkaDeserializer(String json) {
//        try {
//            new ObjectMapper().readValue(json, Candle.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        ObjectMapper om = new ObjectMapper();
//        om.getTypeFactory().constructParametricType(List.class, Candle.class);
//        return new JsonDeserializer<>(om);
//    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public Candle deserialize(String s, byte[] bytes) {
        try {

            ObjectMapper om = new ObjectMapper();
            System.out.println(om.readValue(bytes, Candle.class));
            return om.readValue(bytes, Candle.class);
//            return new ObjectMapper().reader().readValue(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return new ObjectMapper().writer().writeValueAsBytes(candle);

//        Item itemWithOwner = new ObjectMapper().readValue(json, Item.class);

        return null;
    }
}
