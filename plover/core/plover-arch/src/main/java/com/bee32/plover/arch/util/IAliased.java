package com.bee32.plover.arch.util;

import java.util.HashMap;
import java.util.Map;

public interface IAliased {

    /**
     * Get the alias name.
     *
     * @return Non-<code>null</code> alias name.
     */
    String getAlias();

    class AliasMap<T> {

        Map<String, T> map = new HashMap<String, T>();

        public T get(Object key) {
            return map.get(key);
        }

        public T put(String key, T value) {
            return map.put(key, value);
        }

    }

}
