package com.bee32.plover.orm.unit;

import com.bee32.plover.arch.Component;

public abstract class PersistenceUnit
        extends Component
        implements IPersistenceUnit {

    public PersistenceUnit() {
        super();
    }

    public PersistenceUnit(String name) {
        super(name);
    }

    protected abstract Class<? extends IPersistenceUnitContribution> getContributionClass();

    @Override
    public Object get() {
        return null;
    }

}
