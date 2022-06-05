package ir.sahabino.rules_evaluator.factory;

import ir.sahabino.rules_evaluator.entity.Candle;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class CandleCloseTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        Candle candle = (Candle)consumerRecord.value();
        return candle.getCloseTime();

    }
}
