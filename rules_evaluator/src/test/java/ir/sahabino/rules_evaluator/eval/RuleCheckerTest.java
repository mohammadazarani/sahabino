package ir.sahabino.rules_evaluator.eval;

import ir.sahabino.rules_evaluator.entity.Candle;
import ir.sahabino.rules_evaluator.rules.Operand;
import org.junit.Test;

import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.Assert.*;

public class RuleCheckerTest {

    @Test
    public void avgAllDataInPeriod() {

        ArrayBlockingQueue<Candle> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        long currentTimestamp = Instant.now().toEpochMilli();

        for (int i = 1; i < 7; i++) {
            long periodDayMili = i * 24 * 60 * 60 * 1000;
            long rule_date = currentTimestamp - periodDayMili;

            arrayBlockingQueue.add(
                    Candle.build(Integer.toString(i), "3", "0", Integer.toString(i), rule_date, rule_date)
                    .market("BTCUSDT")
            );
        }
        Operand first = new Operand("close", "avg", "5");
        Operand second = new Operand("close", "avg", "0");

        Rule rule = new Rule("test-avg", "BTCUSDT", "gt", first, second);
        RuleChecker ruleChecker = new RuleChecker(rule, arrayBlockingQueue);

        long periodDayMili = 8 * 24 * 60 * 60 * 1000;
        long beforePeriod = currentTimestamp - periodDayMili;

        double close = ruleChecker.avg(beforePeriod, "close");
        System.out.println(close);
        assertEquals (3.5, close, 0.000001);
    }

    @Test
    public void avgSomeDataOutOfPeriod() {

        ArrayBlockingQueue<Candle> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        long currentTimestamp = Instant.now().toEpochMilli();

        for (int i = 5; i < 10; i++) {
            long periodDayMili = i * 24 * 60 * 60 * 1000;
            long rule_date = currentTimestamp - periodDayMili;

            arrayBlockingQueue.add(
                    Candle.build(Integer.toString(i), "3", "0", Integer.toString(i), rule_date, rule_date)
                            .market("BTCUSDT")
            );
        }
        Operand first = new Operand("close", "avg", "5");
        Operand second = new Operand("close", "avg", "0");

        Rule rule = new Rule("test-avg", "BTCUSDT", "gt", first, second);
        RuleChecker ruleChecker = new RuleChecker(rule, arrayBlockingQueue);

        long periodDayMili = 6 * 24 * 60 * 60 * 1000;
        long beforePeriod = currentTimestamp - periodDayMili;

        double close = ruleChecker.avg(beforePeriod, "close");
        assertEquals (5.0, close, 0.000001);
    }


    @Test
    public void meetCondition() {
    }
}