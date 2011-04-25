package com.bee32.plover.arch.util.res;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class MapProperties
        extends AbstractProperties {

    private final Map<String, String> map;

    @SuppressWarnings("unchecked")
    public MapProperties(Properties properties) {
        if (properties == null)
            throw new NullPointerException("properties");
        this.map = (Map<String, String>) (Map<?, ?>) properties;
    }

    public MapProperties(Map<String, String> map) {
        if (map == null)
            throw new NullPointerException("map");
        this.map = map;
    }

    @Override
    public Iterator<Entry<String, String>> iterator() {
        return map.entrySet().iterator();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public String get(String key) {
        return map.get(key);
    }

}
