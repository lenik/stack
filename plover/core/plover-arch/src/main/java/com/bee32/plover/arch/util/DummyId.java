package com.bee32.plover.arch.util;

import java.io.Serializable;

public final class DummyId
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Object outer;

    public DummyId(Object outer) {
        this.outer = outer;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DummyId))
            return false;

        DummyId o = (DummyId) obj;
        return outer == o.outer;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(outer);
    }

    @Override
    public String toString() {
        String typeName = outer.getClass().getSimpleName();
        int sysId = System.identityHashCode(outer);
        return "<" + typeName + "@" + sysId + ">";
    }

}
