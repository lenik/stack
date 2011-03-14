package com.bee32.plover.inject.spring;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.free.AnnotationUtil;
import javax.free.IllegalUsageException;

public class ContextConfigurationUtil {

    public static Collection<String> getContextConfigurationLocations(Class<?> clazz) {

        Set<String> allLocations = new HashSet<String>();
        while (clazz != null) {
            String[] locations = null;
            String[] value = null;
            boolean inheritLocations = false;

            ContextConfiguration cc = AnnotationUtil.getDeclaredAnnotation(clazz, ContextConfiguration.class);
            org.springframework.test.context.ContextConfiguration _cc = AnnotationUtil.getDeclaredAnnotation(clazz,
                    org.springframework.test.context.ContextConfiguration.class);

            if (cc != null && _cc != null)
                throw new IllegalUsageException("Only one of ContextConfiguration annotation could be used on " + clazz);

            if (cc != null) {
                locations = cc.locations();
                value = cc.value();
                inheritLocations = cc.inheritLocations();
            }
            if (_cc != null) {
                locations = _cc.locations();
                value = _cc.value();
                inheritLocations = _cc.inheritLocations();
            }

            if (locations != null) {
                for (String location : locations)
                    allLocations.add(canonicalizeResourceName(clazz, location));

                for (String location : value)
                    allLocations.add(canonicalizeResourceName(clazz, location));

                if (!inheritLocations)
                    break;
            }

            clazz = clazz.getSuperclass();
        }

        return allLocations;
    }

    static String canonicalizeResourceName(Class<?> clazz, String spec) {
        if (spec.contains(":"))
            return spec;

        /** @see RelativeClassResourceName */
        if (spec.startsWith("/"))
            return spec;

        String dirName = clazz.getPackage().getName().replace('.', '/');
        return dirName + "/" + spec;
    }

}
