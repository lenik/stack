package com.bee32.sem.frame.menu;

import javax.free.OrderComparator;

public class MenuSectionComparator
        extends OrderComparator<MenuSection, Integer> {

    MenuSectionComparator() {
    }

    @Override
    public Integer getOrder(MenuSection section) {
        return section.getOrder();
    }

    /**
     * Null sections go last.
     */
    @Override
    public int getNullOrder() {
        return 1;
    }

    public static final MenuSectionComparator INSTANCE = new MenuSectionComparator();

}
