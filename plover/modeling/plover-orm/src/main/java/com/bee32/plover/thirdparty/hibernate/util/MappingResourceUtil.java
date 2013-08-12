package com.bee32.plover.thirdparty.hibernate.util;

import java.util.Set;

import com.bee32.plover.arch.util.ClassCatalog;

/**
 * 映射资源实用工具
 */
public class MappingResourceUtil {

    /**
     * Generate a default mapping resource xml names for class catalog.
     */
    public static String[] getMappingResources(ClassCatalog catalog) {

        Set<Class<?>> classes = catalog.getClasses();

        int size = classes.size();
        String[] mappingResources = new String[size];

        int index = 0;
        for (Class<?> clazz : classes) {

            String className = clazz.getName();

            // foo.Bar -> foo/Bar.hbm.xml
            String hbmPath = className.replace('.', '/') + ".hbm.xml";

            mappingResources[index] = /* "classpath:" + */hbmPath;
        }

        return mappingResources;
    }

}
