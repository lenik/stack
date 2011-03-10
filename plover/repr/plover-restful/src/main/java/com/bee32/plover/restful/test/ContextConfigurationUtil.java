package com.bee32.plover.restful.test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.free.AnnotationUtil;

import org.springframework.test.context.ContextConfiguration;

public class ContextConfigurationUtil {

    public static Collection<String> getContextConfigurationLocations(Class<?> clazz) {

        Set<String> locations = new HashSet<String>();
        while (clazz != null) {
            ContextConfiguration cc = AnnotationUtil.getDeclaredAnnotation(clazz, ContextConfiguration.class);
            if (cc != null) {
                for (String location : cc.locations())
                    locations.add(canonicalizeResourceName(clazz, location));

                for (String location : cc.value())
                    locations.add(canonicalizeResourceName(clazz, location));

                if (!cc.inheritLocations())
                    break;
            }

            clazz = clazz.getSuperclass();
        }

        return locations;
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
