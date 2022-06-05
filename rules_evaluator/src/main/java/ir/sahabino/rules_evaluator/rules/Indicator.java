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


    public void operate(Candle candle) throws InvocationTargetException, IllegalAccessException {
        Optional<Candle> removedElement = candleQueue.put(candle);


        Method firstOperandMethod = getFieldToOperateOn(rule.getFirstOperand().getField());
        Method secondOperandMethod = getFieldToOperateOn(rule.getSecondOperand().getField());

        if (firstSum.isPresent()) {
            firstSum = calculateSum(Integer.parseInt(rule.getFirstOperand().getPeriod()), firstOperandMethod);
            secondSum = calculateSum(Integer.parseInt(rule.getSecondOperand().getPeriod()), secondOperandMethod);
        }else{
            generateNewSum(removedElement, firstOperandMethod, secondOperandMethod);
        }
    }

    private void generateNewSum(Optional<Candle> removedElement, Method firstOperandMethod, Method secondOperandMethod) throws InvocationTargetException, IllegalAccessException {
        Candle removed = removedElement.get();
        Long firstSumValue = this.firstSum.get();
        long newFristValue = firstSumValue - Long.parseLong((String) firstOperandMethod.invoke(removed));
        Long secondSumValue = this.secondSum.get();
        long newSecondValue = secondSumValue - Long.parseLong((String) firstOperandMethod.invoke(removed));

        this.firstSum = Optional.of(newFristValue);
        this.secondSum = Optional.of(newSecondValue);
    }


    //TODO getClose shall be change, based on rules config
    private Optional<Long> calculateSum(int period, Method method) {
        long currentTimestamp = Instant.now().toEpochMilli();
        long periodInMilliSecond = period * 24 * 60 * 60 * 1000;
        long threshold = 1000 * 10; // 10-second
        Optional<Long> sum = candleQueue.stream()
                .filter(candle -> currentTimestamp - candle.getOpenTime() + threshold > periodInMilliSecond)
                .map(candle -> {
                    try {
                        return Long.parseLong((String) method.invoke(candle));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    return 0L;
                }).reduce(Long::sum);
        return sum;
    }

    private Method getFieldToOperateOn(String field) {
        try {
            switch (field.toLowerCase().trim()) {
                case "close":
                    return Candle.class.getMethod("getClose", null);
                case "open":
                    return Candle.class.getMethod("getOpen", null);
                default:
                    throw new NoSuchRuleFieldException();
            }
        } catch (NoSuchMethodException e) {
            throw new NoSuchRuleFieldException();
        }
    }

    private boolean rule

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
