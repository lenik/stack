package com.bee32.xem.zjhf.expr;

import java.math.BigDecimal;
import java.util.Map;

import com.bee32.plover.util.VariableEntry;
import com.bee32.sem.pricing.expr.PricingObject;

/**
 * 方型阀
 */
public class SquareValve
        extends PricingObject {

    BigDecimal length;
    BigDecimal width;

    /**
     * 长度
     *
     * @return
     */
    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    /**
     * 宽度
     *
     * @return
     */
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    @Override
    protected void populateVariables(Map<String, VariableEntry> varMap) {
        super.populateVariables(varMap);
        varMap.put("长度", new VariableEntry(length));
        varMap.put("宽度", new VariableEntry(width));
    }

}
