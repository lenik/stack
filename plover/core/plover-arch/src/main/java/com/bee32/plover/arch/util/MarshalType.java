package com.bee32.plover.arch.util;

public enum MarshalType {

    ID_REF(true),

    ID_VER_REF(true),

    NAME_REF(true),

    OTHER_REF(true),

    SELECTION(false),

    ;

    final boolean reference;

    private MarshalType(boolean reference) {
        this.reference = reference;
    }

    public boolean isReference() {
        return reference;
    }

}
