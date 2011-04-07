package com.bee32.plover.inject.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;

import com.bee32.plover.inject.cref.Import;

public class ImportUtil {

    public static Class<?>[] flatten(Class<?> clazz) {
        return flatten(clazz, false);
    }

    public static Class<?>[] flatten(Class<?> clazz, boolean includeSelf) {
        if (clazz == null)
            throw new NullPointerException("clazz");

        Set<Class<?>> classes = new HashSet<Class<?>>();

        if (includeSelf)
            classes.add(clazz);

        scan(clazz, classes);

        return classes.toArray(new Class<?>[0]);
    }

    static void scan(Class<?> clazz, Set<Class<?>> allClasses) {
        Import _import = clazz.getAnnotation(Import.class);
        if (_import == null)
            return;

        Class<?>[] importClasses = _import.value();
        if (importClasses == null)
            return;

        for (Class<?> importClass : importClasses) {
            boolean included = true;

            if (isEmptyConfig(importClass))
                included = false;

            if (included)
                if (!allClasses.add(importClass))
                    continue;

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

}
