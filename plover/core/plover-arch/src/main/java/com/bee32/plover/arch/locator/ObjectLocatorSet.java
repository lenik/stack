package com.bee32.plover.arch.locator;

import java.util.TreeSet;

public class ObjectLocatorSet
        extends TreeSet<IObjectLocator> {

    private static final long serialVersionUID = 1L;

    public ObjectLocatorSet() {
        super(ObjectLocatorComparator.getInstance());
    }

}