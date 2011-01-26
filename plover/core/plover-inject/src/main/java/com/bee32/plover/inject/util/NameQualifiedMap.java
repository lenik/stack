package com.bee32.plover.inject.util;

public class NameQualifiedMap
        extends QualifiedMap<String> {

    private static final long serialVersionUID = 1L;

    public NameQualifiedMap() {
        super(NameQualifierPreorder.getInstance());
    }

}
