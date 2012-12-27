package com.bee32.sem.material.entity;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

/**
 * 物料类型
 *
 * 成品、半成品、原材料、其它
 *
 * @author jack
 *
 */
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
