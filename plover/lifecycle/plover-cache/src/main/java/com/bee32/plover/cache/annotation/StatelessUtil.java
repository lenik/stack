package com.bee32.plover.cache.annotation;

import java.util.HashMap;
import java.util.Map;

public class StatelessUtil {

    static Map<Class<?>, Object> statelessCache;
    static {
        statelessCache = new HashMap<Class<?>, Object>();
    }

    public static <T> T createOrReuse(Class<T> instanceType)
            throws InstantiationException, IllegalAccessException {

        Stateless stateless = instanceType.getAnnotation(Stateless.class);

        if (stateless == null)
            return instanceType.newInstance();

        Object instance = statelessCache.get(instanceType);
        if (instance == null) {
            synchronized (statelessCache) {
                if (instance == null) {
                    instance = instanceType.newInstance();
                    statelessCache.put(instanceType, instance);
                }
            }
        }
        return instanceType.cast(instance);
    }

}
