package com.bee32.plover.inject.util;

import javax.free.PreorderMap;

public class QualifiedMap<Q, T>
        extends PreorderMap<Q, T> {

    private static final long serialVersionUID = 1L;

    public QualifiedMap(QualifierPreorder<Q> preorder) {
        super(preorder);
    }

}
