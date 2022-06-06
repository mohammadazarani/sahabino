package ir.sahabino.rules_evaluator.eval;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Alert {
    String ruleName;
    String market;
    String currentValue;
    String data;
}
