package com.bee32.plover.orm.unit;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.bee32.plover.arch.util.ClassCatalog;
import com.bee32.plover.orm.util.ObjectPool;
import com.bee32.plover.util.PrettyPrintStream;

public class PUnitDumper {

    public static void dump(PrettyPrintStream out, Class<?> clazz) {
        out.println(clazz);

        ImportUnit annotation = clazz.getAnnotation(ImportUnit.class);
        if (annotation != null) {
            out.enter();
            for (Class<?> c : annotation.value()) {
                dump(out, c);
            }
            out.leave();
        }
    }

    public static String dump(ClassCatalog catalog) {
        PrettyPrintStream out = new PrettyPrintStream();
        dump(out, catalog);
        return out.toString();
    }

    public static void dump(PrettyPrintStream out, ClassCatalog catalog) {
        out.println(catalog.getName() + " @" + ObjectPool.id(catalog));

        Set<? extends ClassCatalog> dependencies = catalog.getDependencies();
        if (!dependencies.isEmpty()) {
            out.enter();
            for (ClassCatalog dependency : dependencies)
                dump(out, dependency);
            out.leave();
        }
    }

    public static String format(PersistenceUnit unit) {
        PrettyPrintStream out = new PrettyPrintStream();

        out.println("Unit Structure: " + unit.getName());
        out.enter();
        dump(out, unit);
        out.leave();

        out.println();
        out.println("Entity Classes: ");
        out.enter();
        {
            // Sort by name.
            Set<String> names = new TreeSet<String>();
            for (Class<?> entityClass : unit)
                names.add(entityClass.getName());

            for (String name : names)
                out.println(name);
        }
        out.leave();

        out.println("Inverse Map: ");
        out.enter();
        {
            Map<Class<?>, ClassCatalog> invMap = unit.getInvMap();

            // sort by catalog name
            Set<String> lines = new TreeSet<String>();
            for (Entry<Class<?>, ClassCatalog> entry : unit.getInvMap().entrySet()) {
                String line = entry.getValue() + " : " + entry.getKey();
                lines.add(line);
            }

            for (String line : lines)
                out.println(line);
        }
        out.leave();

        return out.toString();
    }

}
