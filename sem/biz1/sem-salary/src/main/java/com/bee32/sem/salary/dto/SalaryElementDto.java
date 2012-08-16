package com.bee32.sem.salary.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.salary.entity.SalaryElement;

public class SalaryElementDto
        extends UIEntityDto<SalaryElement, Long> {

    private static final long serialVersionUID = 1L;

    String label;
    String alternate;
    double rate;
    BigDecimal bonus;

    @Override
    protected void _marshal(SalaryElement source) {
        label = source.getLabel();
        bonus = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(SalaryElement target) {
        target.setLabel(label);
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
