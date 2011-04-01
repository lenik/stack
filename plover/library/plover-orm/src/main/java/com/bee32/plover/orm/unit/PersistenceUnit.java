package com.bee32.plover.orm.unit;

import com.bee32.plover.arch.util.ClassCatalog;

public abstract class PersistenceUnit
        extends ClassCatalog {

    public PersistenceUnit(ClassCatalog... imports) {
        super(imports);
    }

    public PersistenceUnit(String name, ClassCatalog... imports) {
        super(name, imports);
    }

    protected abstract void preamble();

    static final PersistenceUnit defaultUnit = new SimplePUnit();

    public static PersistenceUnit getDefault() {
        return defaultUnit;
    }

}
