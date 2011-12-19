package com.bee32.plover.ox1.formula;

import org.springframework.expression.EvaluationException;

public interface IFormulaContext {

    double resolveVariable(String variable)
            throws EvaluationException;

}
