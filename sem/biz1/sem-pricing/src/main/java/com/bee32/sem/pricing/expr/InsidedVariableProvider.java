package com.bee32.sem.pricing.expr;

import java.math.BigDecimal;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.TextMap;

public class InsidedVariableProvider
        implements IPricingVariableProvider {

    @Override
    public BigDecimal evaluate(PricingObject obj, String variableName, TextMap params) {
        Object value = obj.evaluate(variableName, params);
        if (value == null)
            return null;
        else if (value instanceof BigDecimal)
            return (BigDecimal) value;
        else
            throw new IllegalUsageException("Not a BigDecimal value evaluated: " + variableName + " => " + value);
    }

    @Override
    public String[] getVariableNames(PricingObject obj) {
        return obj.getVariableNames();
    }

}
