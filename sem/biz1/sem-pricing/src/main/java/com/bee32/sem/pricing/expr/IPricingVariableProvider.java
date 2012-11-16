package com.bee32.sem.pricing.expr;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;

/**
 * 报价变量提供器
 *
 * 提供报价公式中使用的变量。
 */
public interface IPricingVariableProvider {

    /** 报价日期 */
    String PARAM_DATE = "date";

    /**
     * Evaluate a variable name.
     *
     * @return <code>null</code> if the variable is unknown.
     */
    BigDecimal evaluate(PricingObject obj, String variableName, TextMap params);

    String[] getVariableNames(PricingObject obj);

}
