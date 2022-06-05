package ir.sahabino.rules_evaluator.rules;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Condition {
    GREATER(Long::compareTo, "gt"),
    LOWER((x, y) -> { return y.compareTo(x); }, "lt"),
    ;
    private final Comparator<Long> comparator;
    private final String conditionName;

    Condition(Comparator<Long> comparator, String operatorName) {
        this.comparator = comparator;
        this.conditionName = operatorName;
    }

    private static final Map<String, Condition> VALUE_MAP = Stream.of(values()).
            collect(Collectors.toMap(condition -> condition.conditionName, condition -> condition));


    public static Optional<Condition> fromConditionName(String conditionName) {
        return Optional.ofNullable(VALUE_MAP.get(conditionName));
    }


    public int compare(Optional<Long> first, Optional<Long> second) {
        return comparator.compare(first.get(), second.get());
    }
}
