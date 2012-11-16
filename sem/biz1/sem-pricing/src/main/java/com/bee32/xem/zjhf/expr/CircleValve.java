package com.bee32.xem.zjhf.expr;

import java.math.BigDecimal;
import java.util.Map;

import com.bee32.plover.util.VariableEntry;
import com.bee32.sem.pricing.expr.PricingObject;

/**
 * 圆型阀
 */
public class CircleValve
        extends PricingObject {

    BigDecimal diameter;

    /**
     * 直径
     *
     * @return
     */
    public BigDecimal getDiameter() {
        return diameter;
    }

    public void setDiameter(BigDecimal diameter) {
        this.diameter = diameter;
    }

    @Override
    protected void populateVariables(Map<String, VariableEntry> varMap) {
        super.populateVariables(varMap);
        varMap.put("直径", new VariableEntry(diameter));
    }

}
