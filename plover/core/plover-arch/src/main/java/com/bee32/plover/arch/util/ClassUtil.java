package com.bee32.plover.arch.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.free.IllegalUsageException;
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

    public static Type[] getImmediatePV(Class<?> clazz) {
        Type superclass = clazz.getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            ParameterizedType psuper = (ParameterizedType) superclass;
            return psuper.getActualTypeArguments();
        } else
            return null;
    }

    public static Type[] getOriginPV(Type type) {
        if (type == null)
            throw new NullPointerException("type");

        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType) type;
            return ptype.getActualTypeArguments();
        }

        if (type instanceof Class<?>) {
            Class<?> clazz = (Class<?>) type;
            Type superclass = clazz.getGenericSuperclass();
            return getOriginPV(superclass);
        }

        throw new IllegalUsageException("Not a generic type");
    }

    public static Class<?>[] getOriginPVClass(Type type) {
        Type[] pv = getOriginPV(type);
        if (pv == null)
            return null;

        Class<?>[] pvClasses = new Class<?>[pv.length];
        for (int i = 0; i < pv.length; i++)
            pvClasses[i] = getBound(pv[i]);

        return pvClasses;
    }

    /**
     * @throws Error
     *             If failed to get the type bound.
     */
    public static Class<?> getBound(Type type) {
        while (type instanceof TypeVariable<?>) {
            Type[] bounds = ((TypeVariable<?>) type).getBounds();

            // super or extends?
            Type bound1 = bounds[0];

            type = bound1;
        }

        if (type instanceof ParameterizedType)
            type = ((ParameterizedType) type).getRawType();

        if (!(type instanceof Class<?>))
            throw new Error("Failed to get type bound: " + type);

        return (Class<?>) type;
    }

}
