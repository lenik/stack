package com.bee32.sem.world.thing;

import com.bee32.plover.orm.ext.dict.SimpleNameDictDto;

public class UnitDto
        extends SimpleNameDictDto<Unit> {

    private static final long serialVersionUID = 1L;
    Unit stdUnit;
    double scale;

    public UnitDto() {
        super();
    }

    public Unit getStdUnit() {
        return stdUnit;
    }

    public void setStdUnit(Unit stdUnit) {
        this.stdUnit = stdUnit;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
