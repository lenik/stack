package com.bee32.sem.frame.menu;

import javax.free.AbstractNonNullComparator;

public class MenuEntryComparator
        extends AbstractNonNullComparator<IMenuEntry> {

    MenuEntryComparator() {
    }

    @Override
    public int compareNonNull(IMenuEntry o1, IMenuEntry o2) {
        int order1 = o1.getOrder();
        int order2 = o2.getOrder();
        int cmp = order1 - order2;
        if (cmp != 0)
            return cmp;

        String name1 = o1.getName();
        String name2 = o2.getName();
        cmp = name1.compareTo(name2);
        if (cmp != 0)
            return cmp;

        int id1 = System.identityHashCode(o1);
        int id2 = System.identityHashCode(o2);
        return id1 - id2;
    }

    public static final MenuEntryComparator INSTANCE = new MenuEntryComparator();

}
