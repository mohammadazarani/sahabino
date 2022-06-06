package ir.sahabino.rules_evaluator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.sahabino.rules_evaluator.conf.Conf;
import ir.sahabino.rules_evaluator.dao.KafKaCandleConsumer;
import ir.sahabino.rules_evaluator.entity.Candle;
import ir.sahabino.rules_evaluator.eval.ConditionMeet;
import ir.sahabino.rules_evaluator.eval.Rule;
import ir.sahabino.rules_evaluator.eval.RuleChecker;
import ir.sahabino.rules_evaluator.factory.KafkaConsumerFactory;

import ir.sahabino.rules_evaluator.rules.RuleParser;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;


public class Main {
    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        Conf config = Conf.load();
        KafkaConsumer<String, String> kafkaConsumer = KafkaConsumerFactory.createKafkaConsumer(config);
        KafKaCandleConsumer kafKaCandleConsumer = KafKaCandleConsumer.of(config, kafkaConsumer);
//
        ObjectMapper objectMapper = new ObjectMapper();

        String fileName = "rules.xml";
        List<Rule> rules = RuleParser.parse(fileName);
        ArrayBlockingQueue<Candle> candles = new ArrayBlockingQueue<>(5000);
        List<RuleChecker> ruleCheckers = new ArrayList<>();

        for (Rule rule : rules) {
            ruleCheckers.add(new RuleChecker(rule, candles));
        }
        Thread thread = new Thread(new ConditionMeet(ruleCheckers));
        thread.start();
        while (true) {
            ConsumerRecords<String, String> records = kafKaCandleConsumer.consume();
            for (ConsumerRecord<String, String> record : records) {
                Candle candle = objectMapper.readValue(record.value(), Candle.class);
                candle.setMarket(record.key());
                candles.add(candle);

            }
            //TODO it shall be configurable

            Thread.sleep(3*60*1000);
        }


    }
}
