package com.bee32.sem.world.thing;

import javax.persistence.Entity;

import com.bee32.plover.orm.ext.dict.NameDict;

/**
 * [字典] 单位
 */
@Entity
public class Unit
        extends NameDict {

    private static final long serialVersionUID = 1L;

    public Unit() {
        super();
    }

    public Unit(String name, String label) {
        super(name, label);
    }

    public Unit(String name, String label, String description) {
        super(name, label, description);
    }

    public static final Unit GRAM = new Unit("g", "克");
    public static final Unit KILOGRAM = new Unit("kg", "千克");
    public static final Unit MILLIGRAM = new Unit("mg", "毫克");
    public static final Unit NEWTON = new Unit("N", "牛顿");

    public static final Unit METER = new Unit("m", "米");
    public static final Unit MILLIMETER = new Unit("mm", "毫米");
    public static final Unit CENTIMETER = new Unit("cm", "厘米");
    public static final Unit DECIMETER = new Unit("dm", "分米");
    public static final Unit KILOMETER = new Unit("km", "千米");

    public static final Unit LITER = new Unit("L", "升");
    public static final Unit MILLILITER = new Unit("mL", "毫升"); // == cm3
    public static final Unit CUBIC_METER = new Unit("m3", "立方米");

    public static final Unit SQUARE_METER = new Unit("m2", "平方米");
    public static final Unit SQUARE_CENTIMETER = new Unit("cm2", "平方厘米");
    public static final Unit SQUARE_KILOMETER = new Unit("km2", "平方千米");

}
