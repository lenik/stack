package com.bee32.plover.orm.unit;

import java.util.Set;
import java.util.TreeSet;

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

    public static String format(PersistenceUnit unit) {
        PrettyPrintStream out = new PrettyPrintStream();

        out.println("Unit Structure: " + unit.getName());
        out.enter();
        {
            Class<?> clazz = unit.getClass();
            dump(out, clazz);
        }
        out.leave();

        out.println();
        out.println("Entity Classes: ");
        out.enter();
        {
            Set<String> names = new TreeSet<String>();
            for (Class<?> entityClass : unit)
                names.add(entityClass.getName());

            for (String name : names)
                out.println(name);
        }
        out.leave();
        return out.toString();
    }

}
