package ir.sahabino.rules_evaluator.rules;

import ir.sahabino.rules_evaluator.entity.Candle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Optional;

public class Indicator {
    private Rule rule;
    private CircularFifoQueue<Candle> candleQueue;

    private Optional<Long> firstSum;
    private Optional<Long> secondSum;


    public void operate(Candle candle) {
        Optional<Candle> removedElement = candleQueue.put(candle);
        if (removedElement.isPresent()) {
            Candle removed = removedElement.get();
        }

        Method firstOperandMethod = getFieldToOperateOn(rule.getFirstOperand().getField());
        Method secondOperandMethod = getFieldToOperateOn(rule.getSecondOperand().getField());

        firstSum = calculateSum(Integer.parseInt(rule.getFirstOperand().getPeriod()), firstOperandMethod);
        secondSum = calculateSum(Integer.parseInt(rule.getSecondOperand().getPeriod()), secondOperandMethod);
    }


    //TODO getClose shall be change, based on rules config
    private Optional<Long> calculateSum(int firstPeriod, Method method) {
        long currentTimestamp = Instant.now().toEpochMilli();
        long periodInMilliSecond = firstPeriod * 24 * 60 * 60 * 1000;
        long threshold = 1000 * 10; // 10-second
        firstSum = candleQueue.stream()
                .filter(candle -> candle.getOpenTime() - periodInMilliSecond > threshold)
                .map(candle -> {
                    try {
                        return Long.parseLong((String) method.invoke(candle));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return 0L;
                }).reduce(Long::sum);
        return firstSum;
    }

    private Method getFieldToOperateOn(String field) {
        try {
            switch (field.toLowerCase().trim()) {
                case "close":
                    return Candle.class.getMethod("getClose", null);
                case "open":
                    return Candle.class.getMethod("getOpen", null);
                default:
                    throw new NoSuchRuleFieldExcetion();
            }
        } catch (NoSuchMethodException e) {
            throw new NoSuchRuleFieldExcetion();
        }
    }

//    public double getAvg() {
//        if (sum.isPresent()) {
//            return sum.get() / candleQueue.size();
//        }
//        return
//        return sum / candleQueue.size();
//    }
//
//    public Optional<Long> getSum() {
//        if (sum.isPresent())
//            return sum;
//        return calculateSum();
//    }
}
