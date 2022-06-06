package ir.sahabino.rules_evaluator.eval;

import ir.sahabino.rules_evaluator.entity.Candle;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;

@Getter
@ToString(of = "rule")
public class RuleChecker {
    ArrayBlockingQueue<Candle> arrayBlockingQueue;

    private Rule rule;

    public RuleChecker(Rule rule, ArrayBlockingQueue<Candle> arrayBlockingQueue) {
        this.arrayBlockingQueue = arrayBlockingQueue;
        this.rule = rule;
    }

    public double avg(long beforePeriod, String field) {

        double sum = 0D;
        int counter = 0;
        if (field.equals("close")) {
            for (Candle candle : arrayBlockingQueue) {
                if (candle.getMarket().equals(rule.getMarket())) {
                    if (candle.getOpenTime() > beforePeriod) {
                        sum += Double.parseDouble(candle.getClose());
                        counter++;
                    }
                }
            }
        }
        if (field.equals("open")) {
            for (Candle candle : arrayBlockingQueue) {
                if (candle.getMarket().equals(rule.getMarket())) {
                    if (candle.getOpenTime() > beforePeriod) {
                        sum += Double.parseDouble(candle.getOpen());
                        counter++;
                    }
                }
            }
        }
        if (counter == 0)
            return 0D;
        return sum / counter;
    }


    public Alert meetCondition() {
        long currentTimestamp = Instant.now().toEpochMilli();
        long firstPeriodDayMili = Long.parseLong(rule.getFirstOperand().getPeriod()) * 24 * 60 * 60 * 1000;
        long firstBeforePeriod = currentTimestamp - firstPeriodDayMili;

        long secondPeriodDayMili = Long.parseLong(rule.getSecondOperand().getPeriod()) * 24 * 60 * 60 * 1000;
        long secondBeforePeriod = currentTimestamp - secondPeriodDayMili;


        Optional<Candle> optionalLastCandle = arrayBlockingQueue.stream().
                filter(c1 -> c1.getMarket().equals(rule.getMarket())).
                reduce((c1, c2) -> c2);




        double first = avg(firstBeforePeriod, rule.getFirstOperand().getField());
        double second = avg(secondBeforePeriod, rule.getSecondOperand().getField());


        switch (rule.getOperator()) {
            case "gt":
                if (first > second)
                    if (optionalLastCandle.isPresent()) {
                        Candle lastCandle = optionalLastCandle.get();
                        return new Alert(rule.getRuleName(), rule.getMarket(), lastCandle.getClose(), lastCandle.getCandleOpenData());
                    }
                return null;
            case "lt":
                if (first < second)
                    if (optionalLastCandle.isPresent()) {
                        Candle lastCandle = optionalLastCandle.get();
                        return new Alert(rule.getRuleName(), rule.getMarket(), lastCandle.getClose(), lastCandle.getCandleOpenData());
                    }
                return null;
            default:
                return null;
        }
    }
}
