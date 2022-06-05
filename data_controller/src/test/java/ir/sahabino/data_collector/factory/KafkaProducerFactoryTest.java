//package ir.sahabino.data_collector.factory;
//
//import ir.sahabino.data_collector.entity.Candle;
//import ir.sahabino.data_collector.serializer.CandleSerializer;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.MockProducer;
//import org.apache.kafka.clients.producer.RecordMetadata;
//import org.apache.kafka.common.serialization.StringSerializer;
//
//import java.util.concurrent.Future;
//
//import static org.junit.Assert.*;
//
//public class KafkaProducerFactoryTest {
//    void kafkaProducerTest() {
//        MockProducer mockProducer = new MockProducer<>(true, new StringSerializer(), new CandleSerializer());
//
//        kafkaProducer = new KafkaProducer(mockProducer);
//        Future<Candle> recordMetadataFuture = kafkaProducer.send(
//                "ETHBTC",
//                "{\"data\":null,\"open_time\":1654369560000,\"open\":\"0.05977700\",\"high\":\"0.05978500\",\"low\":\"0.05977700\",\"close\":\"0.05978500\",\"volume\":null,\"close_time\":1654369619999,\"quote_asset_volume\":null,\"number_of_trades\":null,\"taker_buy_base_asset_volume\":null,\"taker_buy_quote_asset_volume\":null}"
//        );
//
//        assertEquals(1, mockProducer.history().size());
//
//    }
//}