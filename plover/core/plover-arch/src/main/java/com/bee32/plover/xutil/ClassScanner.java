package com.bee32.plover.xutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.free.Pred1;

public class ClassScanner {

    Map<Class<?>, Set<Class<?>>> subclassMap = new HashMap<Class<?>, Set<Class<?>>>();

    public synchronized Set<Class<?>> getSubclasses(Class<?> clazz) {
        Set<Class<?>> subclasses = subclassMap.get(clazz);
        if (subclasses == null) {
            subclasses = new HashSet<Class<?>>();
            subclassMap.put(clazz, subclasses);
        }
        return subclasses;
    }

    int addSubclass(Class<?> clazz, Class<?> subclass) {
        Set<Class<?>> set = getSubclasses(clazz);
        int counter = 0;
        if (set.add(subclass))
            counter++;

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null)
            counter += addSubclass(superclass, subclass);

        return counter;
    }

    public int contribute(Class<?> clazz) {
        return addSubclass(clazz, clazz);
    }

    public void scan(String packageName)
            throws IOException {
        ResourceScanner.scanTypes(packageName, new Pred1<Class<?>>() {
            @Override
            public boolean test(Class<?> clazz) {
                int adds = contribute(clazz);
                return adds > 0;
            }
        });
    }

    public int size() {
        return subclassMap.size();
    }

    public int size2() {
        int total = 0;
        for (Set<Class<?>> subclasses : subclassMap.values())
            total += subclasses.size();
        return total;
    }

    public void dump() {
        for (Entry<Class<?>, Set<Class<?>>> entry : subclassMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().size() + " entries");
        }
    }

}
