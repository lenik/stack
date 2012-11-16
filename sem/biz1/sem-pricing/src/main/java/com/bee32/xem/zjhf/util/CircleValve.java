package com.bee32.xem.zjhf.util;

import java.math.BigDecimal;

import com.bee32.sem.pricing.util.PricingObject;

/**
 * 圆型阀
 */
public class CircleValve
        extends PricingObject {

    private static final long serialVersionUID = 1L;

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

}
