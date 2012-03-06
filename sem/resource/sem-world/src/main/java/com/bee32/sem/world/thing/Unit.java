package com.bee32.sem.world.thing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * [字典] 单位
 */
@Entity
public class Unit
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public static final int HINT_LENGTH = 20;

    Unit stdUnit;
    Double scale;
    String hint;

    public Unit() {
        super();
    }

    public Unit(String name, String label, String hint) {
        super(name, label);
        this.hint = hint;
    }

    public Unit(String name, String label, double scale, Unit stdUnit) {
        super(name, label);
        this.stdUnit = stdUnit;
        this.scale = scale;
        this.hint = stdUnit.hint;
    }

    public Unit(String name, String label, double scale, Unit stdUnit, String hint) {
        super(name, label);
        this.stdUnit = stdUnit;
        this.scale = scale;
        this.hint = hint;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof Unit)
            _populate((Unit) source);
        else
            super.populate(source);
    }

    protected void _populate(Unit o) {
        super._populate(o);
        stdUnit = o.stdUnit;
        scale = o.scale;
        hint = o.hint;
    }

    @Column(length = HINT_LENGTH, nullable = false)
    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * Get the standard unit.
     *
     * @return <code>null</code> if this unit itself is a standard unit.
     */
    @ManyToOne
    public Unit getStdUnit() {
        return stdUnit;
    }

    public void setStdUnit(Unit stdUnit) {
        this.stdUnit = stdUnit;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

}
