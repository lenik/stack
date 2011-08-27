package com.bee32.plover.orm.util;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.free.IdentityHashSet;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity._EntityAccessor;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

public class SampleDumper {

    static boolean verboseForAutoEntities = false;

    public static void dump(SamplePackage node) {
        PrettyPrintStream out = new PrettyPrintStream();
        dump(node, out, new IdentityHashSet());
        System.out.println(out);
    }

    static void dump(SamplePackage node, PrettyPrintStream out, IdentityHashSet pset) {
        if (node == null)
            throw new NullPointerException("node");

        out.println("+ " + node.getName() + " @" + System.identityHashCode(node));
        if (!pset.add(node)) {
            out.println("    ...");
            return;
        }

        out.enter();

        TreeMap<String, Entity<?>> sorted = new TreeMap<String, Entity<?>>();
        for (Entity<?> entity : node.getInstances()) {
            String typeName = entity.getClass().getSimpleName();
            Object id = entity.getId();
            String title = typeName + " : " + (id == null ? System.identityHashCode(entity) : id);
            sorted.put(title, entity);
        }

        for (Entry<String, Entity<?>> entry : sorted.entrySet()) {
            String title = entry.getKey();
            Entity<?> entity = entry.getValue();
            out.println(title);

            // Dump auto entity in more detail.
            if (verboseForAutoEntities && _EntityAccessor.isAutoId(entity)) {
                entity.toString(out, FormatStyle.NORMAL);
            }

        }

        for (SamplePackage dep : node.getDependencies())
            dump(dep, out, pset);

        out.leave();
    }

    public static void checkUnique(SamplePackage start) {
        IdentityHashMap<String, Object> map = new IdentityHashMap<String, Object>();
        checkUnique(start, map);
    }

    static void checkUnique(SamplePackage node, Map<String, Object> map) {
        String clazz = node.getName();
        Object existing = map.get(clazz);
        if (existing != null) {
            if (existing != node)
                throw new IllegalStateException("Node " + node + " has different instances.");
        }

        map.put(clazz, node);

        for (SamplePackage dep : node.getDependencies())
            checkUnique(dep, map);
    }

}
