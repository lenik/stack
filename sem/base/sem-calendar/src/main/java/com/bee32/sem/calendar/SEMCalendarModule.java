package com.bee32.sem.calendar;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Base, SEMOids.base.Calendar })
public class SEMCalendarModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/1/10";

    @Override
    protected void preamble() {
    }

}
