package com.bee32.sem.misc;

import java.util.HashMap;
import java.util.Map;

public class AnyOwnerUtil {

    static Map<Class<?>, Boolean> anyOwnerMap = new HashMap<Class<?>, Boolean>();

    public static synchronized Boolean isForAnyOwner(Class<?> clazz) {
        Boolean anyOwner = anyOwnerMap.get(clazz);
        if (anyOwner == null) {
            AnyOwner _anyOwner = clazz.getAnnotation(AnyOwner.class);
            anyOwner = _anyOwner != null;
            anyOwnerMap.put(clazz, anyOwner);
        }
        return anyOwner;
    }

}
