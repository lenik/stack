package com.bee32.sem.frame.ui;

public class TreeMBean<T>
        extends CollectionMBean<T> {

    private static final long serialVersionUID = 1L;

    public TreeMBean(Class<T> elementType, Object context) {
        super(elementType, context);
    }

}
