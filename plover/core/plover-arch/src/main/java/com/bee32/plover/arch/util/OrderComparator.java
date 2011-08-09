package com.bee32.plover.arch.util;

import javax.free.AbstractNonNullComparator;

public class OrderComparator
        extends AbstractNonNullComparator<IOrdered> {

    @Override
    public int compareNonNull(IOrdered o1, IOrdered o2) {
        int cmp = o1.getOrder() - o2.getOrder();
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

    public static final OrderComparator INSTANCE = new OrderComparator();

}
