package com.bee32.plover.arch.util;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.free.StringPart;
import javax.free.UnexpectedException;

public class ClassUtil {

    static boolean useContextClassLoader = false;

    static Class<?> cglibFactoryClass;
    static {
        try {
            cglibFactoryClass = Class.forName("net.sf.cglib.proxy.Factory");
        } catch (ClassNotFoundException e) {
            cglibFactoryClass = null;
        }
    }

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

    public static Class<?> skipProxies(Class<?> clazz) {
        if (clazz == null)
            return null;

        // if (baseName.contains("$$"))
        // return getContextURL(clazz.getSuperclass());
        if (cglibFactoryClass != null)
            if (cglibFactoryClass.isAssignableFrom(clazz))
                return skipProxies(clazz.getSuperclass());

        return clazz;
    }

    public static URL getContextURL(Class<?> clazz) {
        clazz = skipProxies(clazz);

        String className = clazz.getName();
        String baseName = StringPart.afterLast(className, '.');
        if (baseName == null)
            baseName = className;

        String fileName = baseName + ".class";
        URL resource = clazz.getResource(fileName);
        if (resource == null)
            throw new UnexpectedException("The .class file doesn't exist or can't be resolved: " + clazz);
        return resource;
    }

    public static Set<Class<?>> getClasses(Iterable<? extends Object> objects) {
        Set<Class<?>> set = new HashSet<Class<?>>();

        for (Object obj : objects)
            set.add(obj.getClass());

        return set;
    }

}
