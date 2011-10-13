package com.bee32.sem.inventory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bee32.plover.arch.util.EnumAlt;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class Classification
        extends EnumAlt<Character, Classification> {

    private static final long serialVersionUID = 1L;

    static final Map<String, Classification> nameMap = new HashMap<String, Classification>();
    static final Map<Character, Classification> valueMap = new HashMap<Character, Classification>();

    private Classification(char val, String name) {
        super(val, name);
    }

    @Override
    protected Map<String, Classification> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Character, Classification> getValueMap() {
        return valueMap;
    }

    public static Classification forName(String altName) {
        Classification gender = nameMap.get(altName);
        if (gender == null)
            throw new NoSuchEnumException(Classification.class, altName);
        return gender;
    }

    public static Collection<Classification> values() {
        Collection<Classification> values = valueMap.values();
        return Collections.unmodifiableCollection(values);
    }

    public static Classification valueOf(Character value) {
        if (value == null)
            throw new NullPointerException("value");

        Classification gender = valueMap.get(value);
        if (gender == null)
            throw new NoSuchEnumException(Classification.class, value);

        return gender;
    }

    public static Classification valueOf(char value) {
        return valueOf(new Character(value));
    }

    public static final Classification PRODUCT = new Classification('p', "product");
    public static final Classification SEMI = new Classification('s', "semi");
    public static final Classification RAW = new Classification('r', "raw");
    public static final Classification OTHER = new Classification('x', "other");

}
