package ir.sahabino.rules_evaluator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sahabino.rules_evaluator.conf.Conf;
import ir.sahabino.rules_evaluator.dao.KafKaCandleConsumer;
import ir.sahabino.rules_evaluator.entity.Candle;
import ir.sahabino.rules_evaluator.factory.KafkaConsumerFactory;
import ir.sahabino.rules_evaluator.factory.KafkaStreamFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.streams.KafkaStreams;

import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        Conf config = Conf.load();
//        KafkaConsumer<String, String> kafkaConsumer = KafkaConsumerFactory.createKafkaConsumer(config);
//        KafKaCandleConsumer kafKaCandleConsumer = KafKaCandleConsumer.of(config, kafkaConsumer);
//
//        System.out.println(kafkaConsumer.listTopics());
//        ObjectMapper objectMapper = new ObjectMapper();


        KafkaStreams kafkaStream = KafkaStreamFactory.createKafkaStream(config);
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStream::close));
        final CountDownLatch latch = new CountDownLatch(1);
        kafkaStream.setStateListener((newState, oldState) -> {
            if (oldState == KafkaStreams.State.RUNNING && newState != KafkaStreams.State.RUNNING) {
                latch.countDown();
            }
        });
        System.out.println("dad");
        kafkaStream.start();

        try {
            latch.await();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }


//
//        while (true) {
//            ConsumerRecords<String, String> records = kafKaCandleConsumer.consume();
//            for (ConsumerRecord<String, String> record : records) {
//                Candle candle = objectMapper.readValue(record.value(), Candle.class);
//            }
//            System.out.println(records.count());
//            Thread.sleep(1000);
//        }
    }
}
