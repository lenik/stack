package com.bee32.plover.arch.util;

import java.net.URL;

import javax.free.StringPart;
import javax.free.UnexpectedException;

public class ClassUtil {

    static boolean useContextClassLoader = false;

    public static Class<?> forName(String className)
            throws ClassNotFoundException {

        ClassLoader classLoader = ClassUtil.class.getClassLoader();

        if (useContextClassLoader) {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader != null)
                classLoader = contextClassLoader;
        }

        return Class.forName(className, false, classLoader);
    }

    public static URL getContextURL(Class<?> clazz) {
        String className = clazz.getName();
        String baseName = StringPart.afterLast(className, '.');
        if (baseName == null)
            baseName = className;

        if (baseName.contains("$$"))
            return getContextURL(clazz.getSuperclass());

        String fileName = baseName + ".class";
        URL resource = clazz.getResource(fileName);
        if (resource == null)
            throw new UnexpectedException("The .class file doesn't exist or can't be resolved: " + clazz);
        return resource;
    }

}
