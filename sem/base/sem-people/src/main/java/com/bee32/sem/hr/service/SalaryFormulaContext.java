package com.bee32.sem.hr.service;

import org.springframework.expression.EvaluationException;

import com.bee32.plover.ox1.formula.AbstractFormulaContext;

public class SalaryFormulaContext
        extends AbstractFormulaContext {

    @Override
    public double resolveVariable(String variable)
            throws EvaluationException {
        return 0;
    }

}
