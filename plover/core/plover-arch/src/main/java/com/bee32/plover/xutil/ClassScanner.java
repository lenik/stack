package com.bee32.plover.xutil;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.free.Pred1;

import com.bee32.plover.arch.ModuleManager;

public class ClassScanner {

    Set<Class<?>> parsedClasses = new HashSet<Class<?>>();
    Set<Class<?>> rootClasses = new HashSet<Class<?>>();
    Map<Class<?>, Set<Class<?>>> subclassMap = new HashMap<Class<?>, Set<Class<?>>>();

    public synchronized Set<Class<?>> getSubclasses(Class<?> clazz) {
        Set<Class<?>> subclasses = subclassMap.get(clazz);
        if (subclasses == null) {
            subclasses = new HashSet<Class<?>>();
            subclassMap.put(clazz, subclasses);
        }
        return subclasses;
    }

    public Set<Class<?>> getSubclassClosure(Class<?> clazz) {
        Set<Class<?>> closure = new HashSet<Class<?>>();
        _getSubclassClosure(clazz, closure);
        return closure;
    }

    void _getSubclassClosure(Class<?> clazz, Set<Class<?>> closure) {
        if (closure.add(clazz)) {
            for (Class<?> subclass : getSubclasses(clazz))
                _getSubclassClosure(subclass, closure);
        }
    }

    public synchronized int parse(Class<?> clazz) {
        return _parse(clazz);
    }

    private int _parse(Class<?> clazz) {
        if (clazz == ModuleManager.class) {
            System.out.println(1);
        }
        int counter = 0;
        if (parsedClasses.add(clazz)) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null) {
                Set<Class<?>> set = getSubclasses(superclass);
                if (set.add(clazz))
                    counter++;
                counter += _parse(superclass);
            } else
                rootClasses.add(clazz);

            for (Class<?> iface : clazz.getInterfaces()) {
                Set<Class<?>> set = getSubclasses(iface);
                if (set.add(clazz))
                    counter++;
                counter += _parse(iface);
            }
        }
        return counter;
    }

    public void scan(String packageName)
            throws IOException {
        ResourceScanner.scanTypes(packageName, new Pred1<Class<?>>() {
            @Override
            public boolean test(Class<?> clazz) {
                int adds = _parse(clazz);
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

    public void dump(Class<?> root) {
        dump(root, "");
    }

    void dump(Class<?> clazz, String prefix) {
        System.out.println(prefix + clazz.getCanonicalName());
        prefix += "    ";
        for (Class<?> subclass : getSubclasses(clazz)) {
            dump(subclass, prefix);
        }
    }

}
