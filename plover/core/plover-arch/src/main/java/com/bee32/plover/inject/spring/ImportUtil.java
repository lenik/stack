package com.bee32.plover.inject.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;

import com.bee32.plover.inject.cref.Import;

public class ImportUtil {

    /**
     * 这个 flatten 只为 &#64;Configuration 类服务。
     *
     * 应用程序应该使用 ApplicationContextBuilder.selfWire().
     */
    public static Class<?>[] flatten(Class<?> clazz) {
        if (clazz == null)
            throw new NullPointerException("clazz");

        Set<Class<?>> classes = new HashSet<Class<?>>();

        // if (includeSelf) classes.add(clazz);

        scan(clazz, classes);

        return classes.toArray(new Class<?>[0]);
    }

    static void scan(Class<?> clazz, Set<Class<?>> allClasses) {
        if (allClasses.contains(clazz))
            return;

        if (!isEmptyConfig(clazz))
            allClasses.add(clazz);

        Import _import = clazz.getAnnotation(Import.class);
        if (_import == null)
            return;

        Class<?>[] importClasses = _import.value();
        if (importClasses == null)
            return;

        for (Class<?> importClass : importClasses) {
            scan(importClass, allClasses);
        }
    }

    static boolean isEmptyConfig(Class<?> c) {
        for (Field f : c.getDeclaredFields())
            if (f.getAnnotation(Bean.class) != null)
                return false;

        for (Method m : c.getDeclaredMethods())
            if (m.getAnnotation(Bean.class) != null)
                return false;

        Class<?> superclass = c.getSuperclass();
        if (superclass != null && superclass != Object.class)
            return isEmptyConfig(superclass);

        return true;
    }

    public static Set<Class<?>> getImportClasses(Class<?> declaringClass) {
        Set<Class<?>> importClasses = new HashSet<Class<?>>();
        getImportClasses(declaringClass, importClasses);
        return importClasses;
    }

    static void getImportClasses(Class<?> declaringClass, Set<Class<?>> importClasses) {
        Import _import = declaringClass.getAnnotation(Import.class);
        if (_import != null) {
            Class<?>[] v = _import.value();
            for (Class<?> imp : v) {
                importClasses.add(imp);
                getImportClasses(imp, importClasses);
            }
        }

        Class<?> superclass = declaringClass.getSuperclass();
        if (superclass != null)
            getImportClasses(superclass, importClasses);
    }

}
