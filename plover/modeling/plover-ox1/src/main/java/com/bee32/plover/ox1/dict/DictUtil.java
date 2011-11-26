package com.bee32.plover.ox1.dict;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.free.ClassLocal;

public class DictUtil {

    static final ClassLocal<Collection<? extends DictEntity<?>>> clPredefinedInstances;
    static {
        clPredefinedInstances = new ClassLocal<Collection<? extends DictEntity<?>>>();
    }

    public synchronized static <E extends DictEntity<? extends K>, K extends Serializable> //
    Collection<? extends DictEntity<? extends K>> getPredefinedInstances(Class<E> type) {
        List<E> instances = (List<E>) clPredefinedInstances.get(type);
        if (instances != null)
            return instances;

        instances = new ArrayList<E>();
        for (Field field : type.getDeclaredFields()) {
            int modifiers = field.getModifiers();

            if (!Modifier.isPublic(modifiers)) // must be public
                continue;

            if (!Modifier.isStatic(modifiers)) // must be static
                continue;

            // Get the actual type, rather then declared type.
            // if (!type.isAssignableFrom(field.getType()))
            // continue;
            Object _instance;
            try {
                _instance = field.get(null);
            } catch (ReflectiveOperationException e) {
                // logger.warn(...);
                continue;
            }
            if (!type.isInstance(_instance))
                continue;

            @SuppressWarnings("unchecked")
            E instance = (E) _instance;

            instances.add(instance);
        }

        clPredefinedInstances.put(type, instances);
        return instances;
    }

}
