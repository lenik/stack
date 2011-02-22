package com.bee32.plover.orm.unit;

import com.bee32.plover.arch.Component;

public class PersistenceUnit
        extends Component
        implements IPersistenceUnit {

    public PersistenceUnit() {
        super();
    }

    public PersistenceUnit(String name) {
        super(name);
    }

    @Override
    public Object generateHibernateMappings() {
        return null;
    }

}
