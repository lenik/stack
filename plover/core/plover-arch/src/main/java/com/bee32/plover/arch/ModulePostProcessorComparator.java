package com.bee32.plover.arch;

import javax.free.AbstractNonNullComparator;

public class ModulePostProcessorComparator
        extends AbstractNonNullComparator<IModulePostProcessor> {

    @Override
    public int compareNonNull(IModulePostProcessor o1, IModulePostProcessor o2) {
        int priority1 = o1.getPriority();
        int priority2 = o2.getPriority();

        int cmp = priority1 - priority2;
        if (cmp != 0)
            return cmp;

        String n1 = o1.getClass().getName();
        String n2 = o2.getClass().getName();
        cmp = n1.compareTo(n2);
        if (cmp != 0)
            return cmp;

        int id1 = System.identityHashCode(o1);
        int id2 = System.identityHashCode(o2);
        cmp = id1 - id2;
        if (cmp != 0)
            return cmp;

        return 0;
    }

    static final ModulePostProcessorComparator instance = new ModulePostProcessorComparator();

    public static ModulePostProcessorComparator getInstance() {
        return instance;
    }

}
