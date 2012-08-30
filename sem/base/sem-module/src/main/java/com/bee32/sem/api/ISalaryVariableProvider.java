package com.bee32.sem.api;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;

public interface ISalaryVariableProvider {

    String ARG_EMPLOYEE = "employee";
    String ARG_BEGIN_DATE = "beginDate";
    String ARG_END_DATE = "endDate";
    String[] VARIABLE_NAMES = {"基础工资", "岗位补贴", "全勤奖", "学历补贴"};

    /**
     * Evaluate a variable name.
     *
     * @return <code>null</code> if the variable is unknown.
     */
    BigDecimal evaluate(TextMap args, String variableName);

    String[] getVariableNames();

    String getEntityType();

}
