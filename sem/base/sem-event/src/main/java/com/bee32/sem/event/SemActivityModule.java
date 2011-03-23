package com.bee32.sem.event;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Base, SEMOids.base.Event })
public class SemActivityModule
        extends EnterpriseModule {

    @Override
    protected void preamble() {
        exportEntity(Activity.class, Long.class);
        exportEntity(Event.class, Long.class);
        exportEntity(Task.class, Long.class);
    }

}
