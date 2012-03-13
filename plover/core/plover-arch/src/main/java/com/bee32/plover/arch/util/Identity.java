package com.bee32.plover.arch.util;

import java.io.Serializable;

public final class Identity
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Object obj;

    public Identity(Object outer) {
        this.obj = outer;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Identity))
            return false;
        Identity o = (Identity) obj;
        return obj == o.obj;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(obj);
    }

    @Override
    public String toString() {
        String typeName = obj.getClass().getSimpleName();
        int sysId = System.identityHashCode(obj);
        return "<" + typeName + "@" + sysId + ">";
    }

}
