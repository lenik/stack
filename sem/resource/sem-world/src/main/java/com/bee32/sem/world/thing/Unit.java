package com.bee32.sem.world.thing;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.ext.dict.NameDict;

/**
 * [字典] 单位
 */
@Entity
public class Unit
        extends NameDict {

    private static final long serialVersionUID = 1L;

    Unit stdUnit;
    Double scale;

    public Unit() {
        super();
    }

    public Unit(String name, String label) {
        super(name, label);
    }

    public Unit(String name, String label, double scale, Unit stdUnit) {
        super(name, label);
        this.stdUnit = stdUnit;
        this.scale = scale;
    }

    public Unit(String name, String label, String description) {
        super(name, label, description);
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

    public static final Unit GRAM = new Unit("g", "克");
    public static final Unit KILOGRAM = new Unit("kg", "千克", 1000, GRAM);
    public static final Unit MILLIGRAM = new Unit("mg", "毫克", 0.001, GRAM);

    public static final Unit NEWTON = new Unit("N", "牛顿");
    public static final Unit KILONEWTON = new Unit("kN", "千牛顿", 1000, NEWTON);

    public static final Unit METER = new Unit("m", "米");
    public static final Unit MILLIMETER = new Unit("mm", "毫米", 0.001, METER);
    public static final Unit CENTIMETER = new Unit("cm", "厘米", 0.01, METER);
    public static final Unit DECIMETER = new Unit("dm", "分米", 0.1, METER);
    public static final Unit KILOMETER = new Unit("km", "千米", 1000, METER);

    public static final Unit CUBIC_METER = new Unit("m3", "立方米");

    public static final Unit LITER = new Unit("L", "升", 0.001, CUBIC_METER);
    public static final Unit MILLILITER = new Unit("mL", "毫升", 0.000001, CUBIC_METER); // == cm3

    public static final Unit SQUARE_METER = new Unit("m2", "平方米");
    public static final Unit SQUARE_CENTIMETER = new Unit("cm2", "平方厘米", 0.0001, SQUARE_METER);
    public static final Unit SQUARE_KILOMETER = new Unit("km2", "平方千米", 1000000, SQUARE_METER);

}
