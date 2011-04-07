package com.bee32.plover.inject.spring;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.free.AnnotationUtil;
import javax.free.IllegalUsageException;

import com.bee32.plover.inject.cref.Import;

public class ContextConfigurationUtil {

    public static String[] getContextConfigurationLocationArray(Class<?> clazz) {
        Collection<String> locations = getContextConfigurationLocations(clazz);
        String[] array = locations.toArray(new String[0]);
        return array;
    }

    public static Collection<String> getContextConfigurationLocations(Class<?> clazz) {
        Set<String> allLocations = new HashSet<String>();
        scan(clazz, allLocations);
        return allLocations;
    }

    static void scan(Class<?> clazz, Set<String> allLocations) {
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

            Import _import = clazz.getAnnotation(Import.class);
            if (_import != null) {
                for (Class<?> importClass : _import.value()) {
                    scan(importClass, allLocations);
                }
            }

            clazz = clazz.getSuperclass();
        }

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
