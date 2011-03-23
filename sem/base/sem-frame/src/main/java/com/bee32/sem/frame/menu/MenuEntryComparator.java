package com.bee32.sem.frame.menu;

import javax.free.AbstractNonNullComparator;

public class MenuEntryComparator
        extends AbstractNonNullComparator<IMenuItem> {

    MenuEntryComparator() {
    }

    @Override
    public int compareNonNull(IMenuItem o1, IMenuItem o2) {
        MenuSection section1 = o1.getSection();
        MenuSection section2 = o2.getSection();

        int cmp = MenuSectionComparator.INSTANCE.compare(section1, section2);
        if (cmp != 0)
            return cmp;

        int order1 = o1.getOrder();
        int order2 = o2.getOrder();
        cmp = order1 - order2;
        if (cmp != 0)
            return cmp;

        int id1 = System.identityHashCode(o1);
        int id2 = System.identityHashCode(o2);
        return id1 - id2;
    }

    public static final MenuEntryComparator INSTANCE = new MenuEntryComparator();

}
