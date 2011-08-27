package com.bee32.sem.world.thing;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.xp.EntityExt;
import com.bee32.plover.orm.ext.xp.XPool;

@MappedSuperclass
@Green
public abstract class Thing<X extends XPool<?>>
        extends EntityExt<Long, X> {

    private static final long serialVersionUID = 1L;

    private static final int NAME_LENGTH = 40;
    private static final int UNITHINT_LENGTH = 20;

    Unit unit;
    String unitHint;
    UnitConv unitConv;

    public Thing() {
        super();
    }

    public Thing(String name) {
        super(name);
    }

    /**
     * 物品名称（必填）
     */
    @Column(length = NAME_LENGTH, nullable = false)
    public String getName() {
        return name;
    }

    /**
     * 物品名称（必填）
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 物品单位（必填）
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public Unit getUnit() {
        return unit;
    }

    /**
     * 物品单位（必填）
     */
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Column(length = UNITHINT_LENGTH, nullable = false)
    public String getUnitHint() {
        return unitHint;
    }

    public void setUnitHint(String unitHint) {
        if (unitHint == null)
            throw new NullPointerException("unitHint");
        this.unitHint = unitHint;
    }

    /**
     * 物品采用的单位换算表（可选）
     */
    @ManyToOne
    public UnitConv getUnitConv() {
        return unitConv;
    }

    /**
     * 物品采用的单位换算表（可选）
     */
    public void setUnitConv(UnitConv unitConv) {
        this.unitConv = unitConv;
    }

}
