package com.bee32.plover.orm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    static char[] tab = "0123456789abcdef".toCharArray();

    public String abbr(Class<?> clazz) {
        if (clazz == null)
            return null;
        else
            return abbr(clazz.getSimpleName());
    }

    static final int prefixLen = 4;

    public String abbr(String name) {
        if (name.length() <= length)
            return name;

        StringBuilder sb = new StringBuilder(length);

        int lastDot = name.lastIndexOf('.');
        if (lastDot == -1)
            sb.append(name.substring(0, prefixLen));
        else {
            String simpleName = name.substring(lastDot + 1);
            String prefixName;
            if (simpleName.length() > prefixLen)
                prefixName = simpleName.substring(0, prefixLen);
            else
                prefixName = simpleName;
            sb.append(prefixName);
        }

        sb.append('_');

        int hash = hash(name);
        while (sb.length() < length) {
            int digit = hash & 0xf;
            sb.append(tab[digit]);
            hash >>>= 4;
        }

        return sb.toString();
    }

    public void register(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");

        String abbr = abbr(clazz);
        Class<?> existing = map.get(abbr);
        if (existing != null)
            throw new IllegalStateException("Abbreviation collision: " + clazz + " and " + existing);

        map.put(abbr, clazz);
    }

    public Class<?> expand(String abbr)
            throws ClassNotFoundException {
        if (abbr.contains("."))
            return Class.forName(abbr);
        else
            return map.get(abbr);
    }

    private static MessageDigest md5;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    protected static synchronized int hash(String str) {
        byte[] digest;
        digest = md5.digest(str.getBytes());

        int hash = 0;
        for (int i = 0; i < 4; i++)
            hash = hash << 8 | (digest[i] & 0xff);
        return hash;
    }

}
