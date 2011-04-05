package com.bee32.plover.restful;

import javax.free.AbstractNonNullComparator;

public class ViewComparator
        extends AbstractNonNullComparator<IRestfulView> {

    @Override
    public int compareNonNull(IRestfulView o1, IRestfulView o2) {
        int priority1 = o1.getPriority();
        int priority2 = o2.getPriority();

        int cmp = priority1 - priority2;
        if (cmp != 0)
            return cmp;

        cmp = System.identityHashCode(o1) - System.identityHashCode(o2);
        return cmp;
    }

    public static final ViewComparator INSTANCE = new ViewComparator();

}
