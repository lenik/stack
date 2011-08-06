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
        return cmp;
    }

    public static final OrderComparator INSTANCE = new OrderComparator();

}
