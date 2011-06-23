package com.bee32.sem.chance;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Biz1, SEMOids.biz1.chance })
public class SEMChanceModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/6/1";
    public static final String PREFIX_ = PREFIX + "_";

    @Override
    protected void preamble() {
    }

}
