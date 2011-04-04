package com.bee32.plover.orm.unit;

import com.bee32.plover.arch.util.ClassCatalog;

public class SimplePUnit
        extends PersistenceUnit {

    public SimplePUnit(ClassCatalog... imports) {
        super(imports);
    }

    public SimplePUnit(String name, ClassCatalog... imports) {
        super(name, imports);
    }

    @Override
    protected void preamble() {
    }

}
