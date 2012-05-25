package com.bee32.plover.arch.util.res;

import java.util.Map.Entry;
import java.util.Set;

public interface IProperties
        extends Iterable<Entry<String, String>> {

    Set<String> keySet();

    /**
     * @return <code>null</code> If no property is existed for the specfied <code>key</code>.
     */
    String get(String key);

}
