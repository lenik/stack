package com.bee32.plover.orm.util;

import java.util.HashMap;
import java.util.Map;

public class TypeAbbr {

    private final int length;
    private Map<String, Class<?>> map = new HashMap<String, Class<?>>();

    public TypeAbbr(int length) {
        if (length < 5)
            throw new IllegalArgumentException("Length should be >= 5.");
        this.length = length;
    }

    protected int hash(String str) {
        return str.hashCode();
    }

    static char[] tab = "0123456789abcdef".toCharArray();

    protected String compress(String name) {
        if (name.length() <= length)
            return name;

        StringBuilder sb = new StringBuilder(length);
        sb.append(name.substring(0, 4));
        sb.append('_');

        int hash = hash(name);
        for (int i = 5; i < length; i++) {
            int digit = hash & 0xf;
            sb.append(tab[digit]);
            hash >>>= 4;
        }
        return sb.toString();
    }

    public void register(Class<?> clazz) {
        String abbr = compress(clazz.getSimpleName());
        Class<?> existing = map.get(abbr);
        if (existing != null)
            throw new IllegalStateException("Abbreviation collision: " + clazz + " and " + existing);

        map.put(abbr, clazz);
    }

    public Class<?> expand(String abbr) {
        return map.get(abbr);
    }

    public static final int DEFAULT_SIZE = 10;
    public static final TypeAbbr DEFAULT = new TypeAbbr(DEFAULT_SIZE);

}
