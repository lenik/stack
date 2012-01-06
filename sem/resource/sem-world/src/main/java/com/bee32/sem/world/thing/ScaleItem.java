package com.bee32.sem.world.thing;

import java.io.Serializable;

public class ScaleItem
        implements Serializable {

    private static final long serialVersionUID = 1L;

    UnitDto unit;
    double scale;

    public ScaleItem() {
        if(unit == null)
            unit = new UnitDto().ref();
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

    @Override
    public boolean equals(Object obj) {
        ScaleItem item = (ScaleItem) obj;
        if(item.unit.getId().equals(unit.getId()) && item.scale == scale) {
            return true;
        }

        return false;
    }


}
