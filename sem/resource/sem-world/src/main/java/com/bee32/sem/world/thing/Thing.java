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
    public static final int UNIT_HINT_LENGTH = Unit.HINT_LENGTH;

    String serial;

    Unit unit = predefined(Units.class).PIECE;
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

    @Override
    public void populate(Object source) {
        if (source instanceof Thing) {
            Thing<?> o = (Thing<?>) source;
            _populate(o);
        } else
            super.populate(source);
    }

    protected void _populate(Thing<?> o) {
        super._populate(o);
        serial = o.serial;
        unit = o.unit;
        unitHint = o.unitHint;
        unitConv = o.unitConv;
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

    @Column(name = "unitHint", length = UNIT_HINT_LENGTH)
    String get_unitHint() { // default -> null.
        if (unitHint != null)
            if (unitHint.equals(unit.getHint()))
                unitHint = null;
        return unitHint;
    }

    void set_unitHint(String unitHint) {
        this.unitHint = unitHint;
    }

    @Transient
    public String getUnitHint() { // null -> default.
        if (unitHint == null)
            return getUnit().getHint();
        else
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

    /**
     * 假如主单位为 米，1米对应 3公斤，则 addUnitConv(KG, 3.0)
     */
    public void addUnitConv(Unit convUnit, double scale) {
        if (unitConv == null) {
            if (label == null)
                throw new IllegalStateException("label isn't initialized");
            if (unit == null)
                throw new IllegalStateException("unit isn't initialized");
            unitConv = new UnitConv(getLabel(), unit);
        }
        unitConv.setScale(convUnit, scale);
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
