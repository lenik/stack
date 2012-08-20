package com.bee32.sem.salary.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.salary.entity.SalaryElement;
import com.bee32.sem.salary.entity.SalaryElementDef;

public class SalaryElementDto
        extends UIEntityDto<SalaryElement, Long> {

    private static final long serialVersionUID = 1L;

    SalaryDto parent;
    SalaryElementDef def;
    BigDecimal bonus = BigDecimal.ZERO;

    @Override
    protected void _marshal(SalaryElement source) {
        parent = mref(SalaryDto.class, source.getParent());
        def = source.getDef();
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

    public SalaryDto getParent() {
        return parent;
    }

    public void setParent(SalaryDto parent) {
        this.parent = parent;
    }

    public SalaryElementDef getDef() {
        return def;
    }

    public void setDef(SalaryElementDef def){
       this.def = def;
   }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

}
