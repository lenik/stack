package com.bee32.plover.pub.oid;

import java.util.TreeMap;

public class OidMap<T>
        extends TreeMap<OidVector, T> {

    private static final long serialVersionUID = 1L;

    public OidMap() {
        super(OidComparator.getInstance());
    }

}
