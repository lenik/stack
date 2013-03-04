package com.bee32.plover.servlet.test;

import javax.free.AbstractNonNullComparator;

public class ThreadNameComparator
        extends AbstractNonNullComparator<Thread> {

    @Override
    public int compareNonNull(Thread t1, Thread t2) {
        String name1 = t1.getName();
        String name2 = t2.getName();
        int cmp = name1.compareTo(name2);
        if (cmp != 0)
            return cmp;
        return compareIdentity(t1, t2);
    }

    static final ThreadNameComparator instance = new ThreadNameComparator();

    public static ThreadNameComparator getInstance() {
        return instance;
    }

}
