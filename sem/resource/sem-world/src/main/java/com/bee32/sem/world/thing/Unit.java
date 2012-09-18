package com.bee32.sem.world.thing;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.ox1.dict.NameDict;

/**
 * 单位
 *
 * 衡量单位的标准定义。
 */
@Entity
public class Unit
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public static final int HINT_LENGTH = 20;

    Unit stdUnit;
    Double scale;
    String hint = "数量";

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

    /**
     * 单位提示
     *
     * 说明单位的应用场合。如“容量”、“体积“。
     */
    @Column(length = HINT_LENGTH, nullable = false)
    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * 标准单位
     *
     * 衍生单位相关的标准单位。
     *
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

    /**
     * 倍率
     *
     * 相对于标准单位的倍率。如千克（衍生单位）相对于克（标准单位）的倍率为1000。
     */
    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

}
