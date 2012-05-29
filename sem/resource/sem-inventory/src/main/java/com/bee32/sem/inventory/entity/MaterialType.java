package com.bee32.sem.inventory.entity;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

public class MaterialType
        extends EnumAlt<Character, MaterialType> {

    private static final long serialVersionUID = 1L;

    private MaterialType(char val, String name) {
        super(val, name);
    }

    public static MaterialType forName(String name) {
        return forName(MaterialType.class, name);
    }

    public static Collection<MaterialType> values() {
        return values(MaterialType.class);
    }

    public static MaterialType forValue(Character value) {
        return forValue(MaterialType.class, value);
    }

    public static MaterialType forValue(char value) {
        return forValue(new Character(value));
    }

    // public static final MaterialType ALL= new MaterialType('a', "all");
    public static final MaterialType PRODUCT = new MaterialType('p', "product");
    public static final MaterialType SEMI = new MaterialType('s', "semi");
    public static final MaterialType RAW = new MaterialType('r', "raw");
    public static final MaterialType OTHER = new MaterialType('x', "other");

}
