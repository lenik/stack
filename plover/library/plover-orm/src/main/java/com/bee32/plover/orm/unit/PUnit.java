package com.bee32.plover.orm.unit;

import com.bee32.plover.arch.util.ClassCatalog;

public class PUnit
        extends ClassCatalog {

    public PUnit(ClassCatalog... imports) {
        super(imports);
    }

    public PUnit(String name, ClassCatalog... imports) {
        super(name, imports);
    }

    protected void preamble() {
    }

    static final PUnit defaultUnit = new PUnit();

    public static PUnit getDefault() {
        return defaultUnit;
    }

}
