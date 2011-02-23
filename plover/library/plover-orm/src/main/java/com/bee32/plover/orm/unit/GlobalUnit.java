package com.bee32.plover.orm.unit;

import java.util.Collection;

public class GlobalUnit
        extends PersistenceUnit {

    public GlobalUnit() {
        super("global");
    }

    @Override
    protected Class<? extends IPersistenceUnitContribution> getContributionClass() {
        return null;
    }

    @Override
    public Collection<Class<?>> getClasses() {
        return null;
    }

}
