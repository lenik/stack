package com.bee32.plover.arch.util.res;

import java.util.Map.Entry;
import java.util.Set;

public interface IProperties
        extends Iterable<Entry<String, String>> {

    Set<String> keySet();

    String get(String key);

}
