package com.bee32.sem.event;

import com.bee32.plover.orm.unit.PersistenceUnit;

public class SEMEventUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Event.class);
        add(Activity.class);
        add(Task.class);
    }

}
