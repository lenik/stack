package com.bee32.plover.faces.el;

import java.util.Map;
import java.util.Set;

import javax.free.ReadOnlyException;

import com.bee32.plover.faces.utils.YesMap;

public class MapExistence
        extends YesMap<Boolean> {

    final Map<String, ?> orig;

    private MapExistence(Map<String, ?> orig) {
        if (orig == null)
            throw new NullPointerException("orig");
        this.orig = orig;
    }

    @Override
    public boolean containsValue(Object value) {
        return value instanceof Boolean;
    }

    @Override
    public Boolean get(Object key) {
        return orig.containsKey(key);
    }

    @Override
    public Boolean put(String key, Boolean value) {
        throw new ReadOnlyException();
    }

    @Override
    public Set<String> keySet() {
        return orig.keySet();
    }

    public static MapExistence decorate(Map<String, ?> map) {
        if (map instanceof MapExistence)
            return (MapExistence) map;
        else
            return new MapExistence(map);
    }

}
