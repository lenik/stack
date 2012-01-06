package com.bee32.sem.inventory;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

public class Classification
        extends EnumAlt<Character, Classification> {

    private static final long serialVersionUID = 1L;

    private Classification(char val, String name) {
        super(val, name);
    }

    public static Classification forName(String name) {
        return forName(Classification.class, name);
    }

    public static Collection<Classification> values() {
        return values(Classification.class);
    }

    public static Classification valueOf(Character value) {
        return valueOf(Classification.class, value);
    }

    public static Classification valueOf(char value) {
        return valueOf(new Character(value));
    }

    public static final Classification PRODUCT = new Classification('p', "product");
    public static final Classification SEMI = new Classification('s', "semi");
    public static final Classification RAW = new Classification('r', "raw");
    public static final Classification OTHER = new Classification('x', "other");

}
