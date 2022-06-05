package ir.sahabino.rules_evaluator.rules;

import ir.sahabino.rules_evaluator.entity.Candle;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Optional;

public class Indicator {
    private Rule rule;
    private CircularFifoQueue<Candle> candleQueue;

    private Optional<Long> firstSum;
    private Optional<Long> secondSum;
    private int firstPeriod;
    private int secondPeriod;

    public SMA(int firstPeriod, int secondPeriod) {
        this.firstPeriod = firstPeriod;
        this.secondPeriod = secondPeriod;
    }

    public void operate(Candle candle) {
        Optional<E> removedElement = candleQueue.put(candle);
        if (removedElement.isPresent()) {
            E removed = removedElement.get();
        }
        firstSum = calculateSum(rule.getFirstOperand().getPeriod(),);
        secondSum = calculateSum(rule.getSecondOperand().getPeriod());
    }

1
    //TODO getClose shall be change, based on rules config
    private Optional<Long> calculateFirstSum(int firstPeriod) {
        long currentTimestamp = Instant.now().toEpochMilli();
        long periodInMilliSecond = firstPeriod * 24 * 60 * 60 * 1000;
        long threshold = 1000 * 10; // 10-second
        firstSum = candleQueue.stream()
                .filter(candle -> candle.getCloseTime() - periodInMilliSecond > threshold)
                .map(candle -> Long.parseLong(candle.getClose()))
                .reduce(Long::sum);
        return firstSum;
    }


    private Method getFieldToOperateOn(String field){
        field.
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
