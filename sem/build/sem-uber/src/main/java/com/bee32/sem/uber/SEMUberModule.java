package com.bee32.sem.uber;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, 0 })
public class SEMUberModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/0";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
