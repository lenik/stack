package com.bee32.plover.arch.util;

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

}
