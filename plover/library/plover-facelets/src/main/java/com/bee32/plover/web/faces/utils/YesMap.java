package com.bee32.plover.web.faces.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public abstract class YesMap
        implements Map<String, Object> {

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return true;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object put(String key, Object value) {
        return null;
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
    }

    @Override
    public void clear() {
    }

    @Override
    public Set<String> keySet() {
        return Collections.emptySet();
    }

    @Override
    public Collection<Object> values() {
        return Collections.emptyList();
    }

    @Override
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return Collections.emptySet();
    }

}
