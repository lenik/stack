package com.bee32.sem.track;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Support, SEMOids.support.Track })
public class SEMTrackModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/5/2";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
    }

}
