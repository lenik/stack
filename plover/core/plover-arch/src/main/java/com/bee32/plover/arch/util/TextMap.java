package com.bee32.plover.arch.util;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.free.AbstractVariantLookupMap;
import javax.free.Dates;
import javax.free.IllegalUsageException;
import javax.servlet.http.HttpServletRequest;

public class TextMap
        extends AbstractVariantLookupMap<String> {

    Map<String, ?> map;

    public TextMap(Map<String, ?> map) {
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

    public String getNString(String key) {
        String str = getString(key);
        if (str != null) {
            str = str.trim();
            if (str.isEmpty())
                str = null;
        }
        return str;
    }

    @Override
    public String[] getStringArray(String key) {
        return getStringArray(key, new String[0]);
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

    public Object getRaw(String key) {
        return map.get(key);
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
    public boolean getBoolean(String key) {
        Object val = get(key);
        if (val == null)
            return false;
        if ("1".equals(val) || "true".equals(val))
            return true;
        else
            return false;
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

    public Boolean getNBoolean(String key) {
        String val = getString(key);
        if (val == null || val.isEmpty())
            return null;
        else
            return Boolean.valueOf(val);
    }

    public TextMap[] shift(String prefix) {
        if (prefix == null)
            throw new NullPointerException("prefix");
        int n = prefix.length();

        AutoSizeList<Map<String, Object>> maps = new AutoSizeList<Map<String, Object>>();

        for (String key : keySet()) {
            if (!key.startsWith(prefix))
                continue;

            Object valueArray = getRaw(key);
            if (valueArray == null)
                continue;

            if (!valueArray.getClass().isArray())
                throw new IllegalUsageException("Not an array: " + key + " => " + valueArray);

            String subKey = key.substring(n);

            int length = Array.getLength(valueArray);
            for (int i = 0; i < length; i++) {
                Object value = Array.get(valueArray, i);
                Map<String, Object> map = maps.get(i);
                if (map == null) {
                    map = new HashMap<String, Object>();
                    maps.set(i, map);
                }
                map.put(subKey, value);
            }
        }

        TextMap textMaps[] = new TextMap[maps.size()];
        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> map = maps.get(i);
            textMaps[i] = new TextMap(map);
        }

        return textMaps;
    }

    public static TextMap convert(Map<String, ?> map) {
        if (map instanceof TextMap)
            return (TextMap) map;
        else
            return new TextMap(map);
    }

    public static TextMap convert(HttpServletRequest request) {
        Map<String, ?> requestMap = request.getParameterMap();
        return convert(requestMap);
    }

}
