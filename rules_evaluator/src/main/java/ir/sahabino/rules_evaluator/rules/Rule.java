package ir.sahabino.rules_evaluator.rules;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Rule {
    private String market;
    private String operator;
    private Operand firstOperand;
    private Operand secondOperand;


}
