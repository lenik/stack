package com.bee32.sem.api;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;

public interface ISalaryVariableProvider {

    String ARG_EMPLOYEE = "employee";
    String ARG_BEGIN_DATE = "beginDate";
    String ARG_END_DATE = "endDate";
    String ARG_YEARMONTH = "yearMonth";

    /**
     * Evaluate a variable name.
     *
     * @return <code>null</code> if the variable is unknown.
     */
    BigDecimal evaluate(TextMap args, String variableName);

    String[] getVariableNames();

}
