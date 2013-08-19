package com.bee32.sem.event;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.module.EnterpriseModule;

/**
 *
 *
 * <p lang="en">
 */
@Oid({ 3, 15, SEMOids.Base, SEMOids.base.Event })
public class SEMEventModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/1/8";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(Event.class, "event");
        declareEntityPages(EventPriority.class, "priority");
        // NameDict: declareEntityPages(EventCategory.class, "category");
    }

}
