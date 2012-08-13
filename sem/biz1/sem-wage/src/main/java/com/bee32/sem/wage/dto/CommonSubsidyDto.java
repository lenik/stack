package com.bee32.sem.wage.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.wage.entity.CommonSubsidy;

public class CommonSubsidyDto
        extends UIEntityDto<CommonSubsidy, Long> {

    private static final long serialVersionUID = 1L;

    Date date;
    String label;
    BigDecimal bonus;

    @Override
    protected void _marshal(CommonSubsidy source) {
        date = source.getDate();
        label = source.getLabel();
        bonus = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(CommonSubsidy target) {
        target.setDate(date);
        target.setLabel(label);
        target.setBonus(bonus);
    }

    @Override
    protected void _parse(TextMap map)
            throws ParseException {
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
