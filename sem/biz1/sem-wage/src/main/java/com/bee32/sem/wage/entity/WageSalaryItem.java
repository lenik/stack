package com.bee32.sem.wage.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.UIEntityAuto;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "salary_item_seq", allocationSize = 1)
public class WageSalaryItem
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    String lable;
    String alternate;
    double rate;
    BigDecimal bonus;

    public WageSalaryItem() {
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
