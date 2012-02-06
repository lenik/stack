package com.bee32.sem.frame.ui;

import org.apache.commons.collections15.Factory;

public class TreeMBean<T>
        extends CollectionMBean<T> {

    private static final long serialVersionUID = 1L;

    public TreeMBean(Class<T> elementType, Object context) {
        super(elementType, context);
    }

    public TreeMBean(Factory<T> factory, Object context) {
        super(factory, context);
    }

}
