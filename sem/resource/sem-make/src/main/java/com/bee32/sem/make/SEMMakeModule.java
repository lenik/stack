package com.bee32.sem.make;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Resource, SEMOids.resource.Bom })
public class SEMMakeModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/3/4";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
