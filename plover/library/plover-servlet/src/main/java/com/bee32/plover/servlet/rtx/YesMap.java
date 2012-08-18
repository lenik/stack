package com.bee32.plover.servlet.rtx;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * A non-empty but zero-sized, contains "everything" but iterated nothing pseudo-map.
 */
public abstract class YesMap<T>
        implements Map<String, T> {

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
    public T put(String key, T value) {
        return null;
    }

    @Override
    public T remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends T> m) {
    }

    @Override
    public void clear() {
    }

    @Override
    public Set<String> keySet() {
        return Collections.emptySet();
    }

    @Override
    public Collection<T> values() {
        return Collections.emptyList();
    }

    @Override
    public Set<java.util.Map.Entry<String, T>> entrySet() {
        return Collections.emptySet();
    }

}
