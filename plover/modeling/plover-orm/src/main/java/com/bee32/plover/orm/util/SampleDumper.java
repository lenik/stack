package com.bee32.plover.orm.util;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.free.IdentityHashSet;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

public class SampleDumper {

    static boolean verboseForAutoEntities = false;

    public static void dump(SamplePackage node) {
        PrettyPrintStream out = new PrettyPrintStream();

        Map<SamplePackage, List<SamplePackage>> xrefMap = new IdentityHashMap<>();

        Set<SamplePackage> roots = new LinkedHashSet<>();
        for (SamplePackage pkg : node.getAllDependencies()) {
            Set<SamplePackage> deps = pkg.getDependencies();
            if (deps.isEmpty())
                roots.add(pkg);
            else
                for (SamplePackage dep : deps) {
                    List<SamplePackage> users = xrefMap.get(dep);
                    if (users == null)
                        xrefMap.put(dep, users = new ArrayList<>());
                    users.add(pkg);
                }
        }

        for (SamplePackage root : roots)
            dump(out, root, xrefMap, new IdentityHashSet());
        System.out.println(out);
    }

    static void dump(PrettyPrintStream out, SamplePackage start, Map<SamplePackage, List<SamplePackage>> xrefMap,
            IdentityHashSet pset) {
        if (start == null)
            throw new NullPointerException("start");

        out.print("+ " + start.getName() + " @" + System.identityHashCode(start));
        if (!pset.add(start)) {
            out.println(" (...)");
            return;
        }

        out.println();
        out.enter();

        TreeMap<String, Entity<?>> sortedSamples = new TreeMap<String, Entity<?>>();
        for (Entity<?> sample : start.getSamples()) {
            String typeName = sample.getClass().getSimpleName();
            Object id = sample.getId();
            String title = typeName + " : " + (id == null ? System.identityHashCode(sample) : id);
            sortedSamples.put(title, sample);
        }

        for (Entry<String, Entity<?>> sample : sortedSamples.entrySet()) {
            String title = sample.getKey();
            Entity<?> entity = sample.getValue();
            out.println(title);

            // Dump auto entity in more detail.
            if (verboseForAutoEntities && EntityAccessor.isAutoId(entity)) {
                entity.toString(out, FormatStyle.NORMAL);
            }
        }

        List<SamplePackage> users = xrefMap.get(start);
        if (users != null)
            for (SamplePackage user : users)
                dump(out, user, xrefMap, pset);

        out.leave();
    }

    /**
     * @param bottom
     *            Sample package towards the bottom side.
     */
    public static void checkUnique(SamplePackage bottom) {
        IdentityHashMap<String, Object> map = new IdentityHashMap<String, Object>();
        checkUnique(bottom, map);
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
