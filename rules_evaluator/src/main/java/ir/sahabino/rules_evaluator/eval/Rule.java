package ir.sahabino.rules_evaluator.eval;

import ir.sahabino.rules_evaluator.entity.Candle;
import lombok.Getter;

import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;

@Getter
public class Rule {
    ArrayBlockingQueue<Candle> arrayBlockingQueue;

    private String ruleName;
    private String market;
    private long firstPeriod;
    private long secondPeriod;
    private String firstField;
    private String secondField;
    private String operator;

    public Rule(String ruleName, ArrayBlockingQueue<Candle> arrayBlockingQueue, String market, long firstPeriod, long secondPeriod, String firstField, String secondField, String operator) {
        this.arrayBlockingQueue = arrayBlockingQueue;
        this.firstPeriod = firstPeriod;
        this.secondPeriod = secondPeriod;
        this.firstField = firstField;
        this.secondField = secondField;
        this.market = market;
        this.ruleName = ruleName;
        this.operator = operator;
    }

    public double avg(long beforePeriod){
        double sum = 0D;
        int counter = 0;
        if (firstField.equals("close")) {
            for (Candle candle : arrayBlockingQueue) {
                if (candle.getMarket().equals(this.market)) {
                    if (candle.getOpenTime() > beforePeriod) {
                        sum += Double.parseDouble(candle.getClose());
                        counter++;
                    }
                }
            }
        }
        if (firstField.equals("open")) {
            for (Candle candle : arrayBlockingQueue) {
                if (candle.getMarket().equals(this.market)) {
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

    @Override
    public String toString() {
        return "Rule{" +
                "ruleName='" + ruleName + '\'' +
                ", market='" + market + '\'' +
                ", firstPeriod=" + firstPeriod +
                ", secondPeriod=" + secondPeriod +
                ", firstField='" + firstField + '\'' +
                ", secondField='" + secondField + '\'' +
                ", operator='" + operator + '\'' +
                '}';
    }

    public boolean meetCondition() {
        long currentTimestamp = Instant.now().toEpochMilli();
        long firstPeriodDayMili = firstPeriod * 24 * 60 * 60 * 1000;
        long firstBeforePeriod = currentTimestamp - firstPeriodDayMili;

        long secondPeriodDayMili = secondPeriod * 24 * 60 * 60 * 1000;
        long secondBeforePeriod = currentTimestamp - secondPeriodDayMili;

        double first = avg(firstBeforePeriod);
        double second = avg(secondBeforePeriod);


        switch (this.operator) {
            case "gt":
                System.out.println("------");
                System.out.println(first);
                System.out.println(second);
                return first > second;
            case "lt":
                return first < second;
            default:
                return false;
        }
    }
}
