package com.bee32.sem.event;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.module.EnterpriseModule;

public class SemActivityModule
        extends EnterpriseModule {

    PersistenceUnit persistenceUnit;
    {
        persistenceUnit = new PersistenceUnit();
        persistenceUnit.addPersistedClass(Activity.class);
        persistenceUnit.addPersistedClass(Event.class);
        persistenceUnit.addPersistedClass(Task.class);
    }

    @Override
    protected void preamble() {
        declare(persistenceUnit);
    }

}
