package com.bee32.sem.world;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.world.thing.Unit;

/**
 *
 *
 * <p lang="en">
 * SEM World Module
 */
@Oid({ 3, 15, SEMOids.Resource, SEMOids.resource.World })
public class SEMWorldModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/3/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(Unit.class, "unit");
    }

}
