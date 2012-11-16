package com.bee32.sem.pricing.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.bee32.sem.pricing.entity.PricingFormula;

/**
 * 报价对像
 */
public class PricingObject
        implements Serializable {

    private static final long serialVersionUID = 1L;

    float discount;
    BigDecimal quantity;
    Date date;

    PricingFormula formula;

    /**
     * 折扣
     *
     * 中国式折扣
     */
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    /**
     * 数量
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * 报价日期
     *
     * @return
     */
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 公式
     *
     * @return
     */
    public PricingFormula getFormula() {
        return formula;
    }

    public void setFormula(PricingFormula formula) {
        this.formula = formula;
    }

}
