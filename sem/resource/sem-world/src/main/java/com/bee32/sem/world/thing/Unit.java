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

    public static final Unit g = new Unit("g", "克");
    public static final Unit kg = new Unit("kg", "千克");
    public static final Unit mg = new Unit("mg", "毫克");
    public static final Unit N = new Unit("L", "牛顿");

    public static final Unit m = new Unit("m", "米");
    public static final Unit mm = new Unit("mm", "毫米");
    public static final Unit cm = new Unit("cm", "厘米");
    public static final Unit dm = new Unit("dm", "分米");
    public static final Unit km = new Unit("km", "千米");

    public static final Unit L = new Unit("L", "升");
    public static final Unit mL = new Unit("mL", "毫升"); // == cm3
    public static final Unit m3 = new Unit("m3", "立方米");

    public static final Unit m2 = new Unit("m2", "平方米");
    public static final Unit cm2 = new Unit("cm2", "平方厘米");
    public static final Unit km2 = new Unit("km2", "平方千米");

}
