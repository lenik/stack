package com.bee32.plover.arch.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections15.map.AbstractMapDecorator;

public class MapStruct
        extends AbstractMapDecorator<String, Object>
        implements IStruct {

    public MapStruct() {
        super(new HashMap<String, Object>());
    }

    @SuppressWarnings("unchecked")
    public MapStruct(Map<String, ?> map) {
        super((Map<String, Object>) (Map<?, ?>) map);
    }

    @Override
    public <T> T getScalar(String key) {
        Object value = get(key);
        return toScalar(value);
    }

    @Override
    public <T> T[] getArray(String key) {
        Object value = get(key);
        return toArray(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T toScalar(Object value) {
        if (value == null)
            return null;

        if (value.getClass().isArray())
            return (T) Array.get(value, 0);

        return (T) value;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Object value) {
        if (value == null)
            return null;

        if (value.getClass().isArray())
            return (T[]) value;

        Object array = Array.newInstance(value.getClass(), 1);
        Array.set(array, 0, value);
        return (T[]) array;
    }

}
