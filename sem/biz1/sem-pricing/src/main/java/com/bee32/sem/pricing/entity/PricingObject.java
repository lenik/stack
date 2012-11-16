package com.bee32.sem.pricing.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 报价对像
 *
 */
public class PricingObject implements Serializable {
    private static final long serialVersionUID = 1L;

    float discount;
    BigDecimal amount;
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
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 报价日期
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
     * @return
     */
    public PricingFormula getFormula() {
        return formula;
    }

    public void setFormula(PricingFormula formula) {
        this.formula = formula;
    }
}
