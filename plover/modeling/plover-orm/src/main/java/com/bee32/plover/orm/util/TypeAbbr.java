package com.bee32.plover.orm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.postgresql.util.Base64;

public class TypeAbbr {

    private final int length;
    private Map<String, Class<?>> map = new HashMap<String, Class<?>>();

    public TypeAbbr(int length) {
        if (length < 5)
            throw new IllegalArgumentException("Length should be >= 5.");
        this.length = length;
    }

    public String abbr(Class<?> clazz) {
        if (clazz == null)
            return null;
        else
            return abbr(clazz.getName());
    }

    static final int prefixLen = 4;

    public String abbr(String name) {
        // if (name.length() <= length) return name;

        StringBuilder sb = new StringBuilder(length);

        int lastDot = name.lastIndexOf('.');
        String simpleName = lastDot == -1 ? name : name.substring(lastDot + 1);

        if (simpleName.length() > prefixLen)
            simpleName = simpleName.substring(0, prefixLen);
        sb.append(simpleName);

        sb.append('_');

        String hash = hash(name);
        hash = hash.substring(0, length - sb.length());
        sb.append(hash);

        return sb.toString();
    }

    public String register(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");

        String abbr = abbr(clazz);
        Class<?> existing = map.get(abbr);
        if (existing != null && existing != clazz)
            throw new IllegalStateException("Abbreviation collision: " + clazz + " and " + existing);

        map.put(abbr, clazz);
        return abbr;
    }

    public Class<?> expand(String abbr)
            throws ClassNotFoundException {
        if (abbr == null)
            return null;

        if (abbr.contains("."))
            return Class.forName(abbr);

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

    protected static synchronized String hash(String str) {
        byte[] digest;
        digest = md5.digest(str.getBytes());

        String base64 = Base64.encodeBytes(digest);
        return base64;
    }

}
