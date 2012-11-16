package com.bee32.sem.pricing.entity;

import java.math.BigDecimal;

/**
 * 方型阀
 *
 */
public class SquareValve extends PricingObject {

    private static final long serialVersionUID = 1L;

    BigDecimal length;
    BigDecimal width;

    /**
     * 长度
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
     * @return
     */
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }



}
