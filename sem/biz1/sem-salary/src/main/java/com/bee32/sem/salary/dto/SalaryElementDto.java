package com.bee32.sem.salary.dto;

import java.math.BigDecimal;

import javax.free.ParseException;

import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.plover.arch.util.TextMap;
import com.bee32.plover.ox1.color.UIEntityDto;
import com.bee32.sem.salary.entity.SalaryElement;

public class SalaryElementDto
        extends UIEntityDto<SalaryElement, Long>
        implements IEnclosedObject<SalaryDto>, Comparable<SalaryElementDto> {

    private static final long serialVersionUID = 1L;

    SalaryDto parent;
    SalaryElementDefDto def;
    BigDecimal bonus = BigDecimal.ZERO;

    @Override
    public SalaryDto getEnclosingObject() {
        return parent;
    }

    @Override
    public void setEnclosingObject(SalaryDto enclosingObject) {
        parent = enclosingObject;
    }

    @Override
    protected void _marshal(SalaryElement source) {
        parent = mref(SalaryDto.class, source.getParent());
        def = mref(SalaryElementDefDto.class, source.getDef());
        bonus = source.getBonus();
    }

    @Override
    protected void _unmarshalTo(SalaryElement target) {
        merge(target, "parent", parent);
        merge(target, "def", def);
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

    public SalaryElementDefDto getDef() {
        return def;
    }

    public void setDef(SalaryElementDefDto def) {
        this.label = def.getLabel();
        this.def = def;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    @Override
    public int compareTo(SalaryElementDto o) {
        if (o == null)
            return -1;
        int cmp = this.def.order - o.def.order;
        if (cmp != 0)
            return cmp;
        int id1 = System.identityHashCode(this);
        int id2 = System.identityHashCode(o);
        return id1 - id2;
    }

}
