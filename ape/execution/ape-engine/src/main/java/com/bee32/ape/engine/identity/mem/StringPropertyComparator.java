package com.bee32.ape.engine.identity.mem;

import javax.free.AbstractNonNullComparator;

public abstract class StringPropertyComparator<T>
        extends AbstractNonNullComparator<T> {

    @Override
    public int compareNonNull(T o1, T o2) {
        String property1 = getTheProperty(o1);
        String property2 = getTheProperty(o2);
        int cmp = property1.compareTo(property2);
        if (cmp != 0)
            return cmp;
        else
            return compareIdentity(o1, o2);
    }

    protected abstract String getTheProperty(T obj);

}
