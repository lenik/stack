package com.bee32.sem.salary.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;

/**
 * 加班参数提供器
 *
 * <p lang="en">
 * Work Overtime Variable Provider
 */
public class WorkOvertimeVariableProvider
        extends AbstractSalaryVariableProvider {

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {

        return null;
    }

    @Override
    public String[] getVariableNames() {
        return new String[0];
    }

}
