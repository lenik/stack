package com.bee32.plover.arch.util.dto;

import java.util.IdentityHashMap;

public class ObjectIndex {

    static IdentityHashMap<Object, Integer> index = new IdentityHashMap<>();
    static int nextIndex = 0;

    public static String objid(Object obj) {
        if (obj == null)
            return "(null)";
        Integer id = index.get(obj);
        if (id == null)
            index.put(obj, id = ++nextIndex);
        return obj.getClass().getSimpleName() + "^" + id;
    }

}
