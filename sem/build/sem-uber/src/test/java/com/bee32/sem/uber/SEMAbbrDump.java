package com.bee32.sem.uber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bee32.plover.orm.util.ITypeAbbrAware;

public class SEMAbbrDump
        implements ITypeAbbrAware {

    public static void main(String[] args) {

        SEMUberUnit unit = new SEMUberUnit();
        List<Class<?>> classes = new ArrayList<Class<?>>(unit.getClasses());

        Collections.sort(classes, SimpleNameComparator.INSTANCE);

        for (Class<?> clazz : classes) {
            String abbr = ABBR.register(clazz);
            String simpleName = clazz.getSimpleName();
            if (!simpleName.equals(abbr))
                System.out.println(abbr + ":" + clazz.getName());
        }
    }

}
