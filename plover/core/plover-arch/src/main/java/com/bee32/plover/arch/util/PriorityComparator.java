package com.bee32.plover.arch.util;

import java.io.Serializable;

import javax.free.AbstractNonNullComparator;

public class PriorityComparator
        extends AbstractNonNullComparator<IPriority>
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public int compareNonNull(IPriority o1, IPriority o2) {
        int cmp = o1.getPriority() - o2.getPriority();
        if (cmp != 0)
            return cmp;

        int h1 = System.identityHashCode(o1);
        int h2 = System.identityHashCode(o2);
        cmp = h1 - h2;
        if (cmp != 0)
            return cmp;

        if (!o1.equals(o2))
            return -2;

        return 0;
    }

    public static final PriorityComparator INSTANCE = new PriorityComparator();

}
