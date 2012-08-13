package com.bee32.sem.wage.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.wage.entity.WageSalaryItem;

public class WageSalaryItemDto
        extends UIEntityDto<WageSalaryItem, Long> {

    private static final long serialVersionUID = 1L;

    String label;
    String alternate;
    double rate;
    BigDecimal bonus;

    @Override
    protected void _marshal(WageSalaryItem source) {
        label = source.getLabel();
        alternate = source.getAlternate();
        rate = source.getRate();
        bonus = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(WageSalaryItem target) {
        target.setLabel(label);
        target.setAlternate(alternate);
        target.setRate(rate);
        target.setBonus(bonus);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
