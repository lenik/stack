package com.bee32.plover.arch.util;

import javax.free.AbstractNLS;

public class EmptyNLS
        extends AbstractNLS {

    @Override
    protected Object localGet(String s, Object obj) {
        return null;
    }

    @Override
    protected String localName() {
        return null;
    }

    @Override
    protected void reload()
            throws Exception {
    }

    public static final EmptyNLS INSTANCE = new EmptyNLS();

}
