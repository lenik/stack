package com.bee32.plover.arch.util;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.free.AbstractVariantLookupMap;
import javax.free.Dates;

public class ParameterMap
        extends AbstractVariantLookupMap<String> {

    Map<String, ?> map;

    public ParameterMap(Map<String, ?> map) {
        if (map == null)
            throw new NullPointerException("map");
        this.map = map;
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public String[] getStringArray(String key) {
        return getStringArray(key, null);
    }

    @Override
    public String[] getStringArray(String key, String[] defaultValue) {
        Object value = map.get(key);

        if (value == null)
            return defaultValue;

        if (value.getClass().isArray())
            return (String[]) value;

        String[] array = { String.valueOf(value) };
        return array;
    }

    @Override
    public Object get(String key) {
        Object value = map.get(key);

        if (value == null)
            return null;

        if (value.getClass().isArray()) {
            // if (Array.getLength(value) == 0)
            // return null;
            return Array.get(value, 0);
        }

        return value;
    }

    @Override
    public Date getDate(String key, Date defaultValue) {
        String s = getString(key);
        if (s == null)
            return defaultValue;

        try {
            return Dates.YYYY_MM_DD.parse(s);
        } catch (java.text.ParseException e) {
            return defaultValue;
        }
    }

    @Override
    public Date getDate(String key) {
        return getDate(key, null);
    }

    public Byte getNByte(String key) {
        String val = getString(key);
        if (val == null || val.isEmpty())
            return null;
        else
            return Byte.valueOf(val);
    }

    public Short getNShort(String key) {
        String val = getString(key);
        if (val == null || val.isEmpty())
            return null;
        else
            return Short.valueOf(val);
    }

    public Integer getNInt(String key) {
        String val = getString(key);
        if (val == null || val.isEmpty())
            return null;
        else
            return Integer.valueOf(val);
    }

    public Long getNLong(String key) {
        String val = getString(key);
        if (val == null || val.isEmpty())
            return null;
        else
            return Long.valueOf(val);
    }

    public Float getNFloat(String key) {
        String val = getString(key);
        if (val == null || val.isEmpty())
            return null;
        else
            return Float.valueOf(val);
    }

    public Double getNDouble(String key) {
        String val = getString(key);
        if (val == null || val.isEmpty())
            return null;
        else
            return Double.valueOf(val);
    }

}
