package com.bee32.sem.world.thing;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.DummyId;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.xp.EntityExt;
import com.bee32.plover.ox1.xp.XPool;

@MappedSuperclass
@Green
public abstract class Thing<X extends XPool<?>>
        extends EntityExt<Long, X> {

    private static final long serialVersionUID = 1L;

    public static final int SERIAL_LENGTH = 32;
    public static final int UNIT_HINT_LENGTH = 20;

    String serial;

    Unit unit;
    String unitHint;
    UnitConv unitConv;

    public Thing() {
        super();
    }

    public Thing(String name) {
        super(name);
    }

    public Thing(String name, String serial) {
        super(name);
        if (serial == null)
            throw new NullPointerException("serial");
        this.serial = serial;
    }

    /**
     * 物品编码、物品序列号
     */
    @NaturalId(mutable = true)
    @Column(length = SERIAL_LENGTH)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * 物品名称（必填）
     */
    @Transient
    public String getDisplayName() {
        return label;
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

    @Column(length = UNIT_HINT_LENGTH, nullable = false)
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

    @Override
    protected Serializable naturalId() {
        if (serial == null)
            return new DummyId(this);
        else
            return serial;
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (serial == null)
            return null;
        return new Equals(prefix + "serial", serial);
    }

}
