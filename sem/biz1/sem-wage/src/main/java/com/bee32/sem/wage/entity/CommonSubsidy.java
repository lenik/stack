package com.bee32.sem.wage.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.bee32.plover.ox1.color.UIEntityAuto;

public class CommonSubsidy
        extends UIEntityAuto<Long> {

    private static final long serialVersionUID = 1L;
    Date date;
    String label;
    BigDecimal bonus;

    public CommonSubsidy() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
