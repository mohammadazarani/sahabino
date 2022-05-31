package ir.sahabino.rules_evaluator;

import ir.sahabino.rules_evaluator.conf.Conf;
import ir.sahabino.rules_evaluator.dao.KafKaCandleConsumer;
import ir.sahabino.rules_evaluator.factory.KafkaConsumerFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Main {
    public static void main(String[] args) {
        Conf config = Conf.load();
        KafkaConsumer<String, String> kafkaConsumer = KafkaConsumerFactory.createKafkaConsumer(config);

        KafKaCandleConsumer kafKaCandleConsumer = KafKaCandleConsumer.of(config, kafkaConsumer);
        while (true) {
            ConsumerRecords<String, String> records = kafKaCandleConsumer.consume();

            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
                System.out.println(record.key());
            }
            System.out.println(records.count());

        }
    }
}
