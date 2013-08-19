package com.bee32.sem.salary.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;

/**
 * 基础补贴参数提供器
 *
 * <p lang="en">
 * Base Bonus Variable Provider
 */
public class BaseBonusVariableProvider
        extends AbstractSalaryVariableProvider {

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
// BaseBonus first = DATA(BaseBonus.class).getFirst(//
// new Equals("label", variableName), //
// Order.desc("beginTime"));

// if (first == null)
        return null;

// return first.getBonus();
    }

    @Override
    public String[] getVariableNames() {
        return new String[0];
    }

}
