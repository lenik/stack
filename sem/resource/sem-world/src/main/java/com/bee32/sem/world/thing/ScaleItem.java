package com.bee32.sem.world.thing;

import java.io.Serializable;

public class ScaleItem
        implements Serializable {

    private static final long serialVersionUID = 1L;

    UnitDto unit;
    double scale;

    public ScaleItem() {
        unit = new UnitDto().create();
    }

    public UnitDto getUnit() {
        return unit;
    }

    public void setUnit(UnitDto unit) {
        this.unit = unit;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

}
