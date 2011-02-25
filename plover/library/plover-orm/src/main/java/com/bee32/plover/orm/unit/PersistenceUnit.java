package com.bee32.plover.orm.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bee32.plover.arch.service.ServiceContribution;

public class PersistenceUnit
        extends ServiceContribution<IPersistenceUnit>
        implements IPersistenceUnit {

       private List<Class<?>> classes = new ArrayList<Class<?>>();

    public PersistenceUnit() {
        super(GLOBAL, IPersistenceUnit.class);
    }

    public PersistenceUnit(String name) {
        super(name, IPersistenceUnit.class);
    }

    @Override
    public Collection<Class<?>> getClasses() {
        return classes;
    }

    public void addPersistedClass(Class<?> clazz) {
        classes.add(clazz);
    }

    public void removePersistedClass(Class<?> clazz) {
        classes.remove(clazz);
    }

}
