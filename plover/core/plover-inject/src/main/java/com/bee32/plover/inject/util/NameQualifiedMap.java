package com.bee32.plover.inject.util;

public class NameQualifiedMap<T>
        extends QualifiedMap<String, T> {

    private static final long serialVersionUID = 1L;

    public NameQualifiedMap() {
        super(NameQualifierPreorder.getInstance());
    }

}
