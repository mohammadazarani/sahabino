package ir.sahabino.rules_evaluator.rules;

import java.util.Comparator;
import java.util.function.LongBinaryOperator;

public enum Condition {
    GREATER(Long::compareTo, "gt"),
    LOWER((x, y) -> y.compareTo(x), "lt"),
    ;

    private final Comparator<Long> comparator;
    private final String operatorName;
    Condition(Comparator<Long> comparator, String operatorName) {
        this.comparator = comparator;
        this.operatorName = operatorName;
    }


}
