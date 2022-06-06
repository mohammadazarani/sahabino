package ir.sahabino.rules_evaluator.eval;

import ir.sahabino.rules_evaluator.rules.Operand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Rule {
    String ruleName;
    String market;
    String operator;

    Operand firstOperand;
    Operand secondOperand;
}
