package com.bee32.sem.track;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.track.entity.Issue;

@Oid({ 3, 15, SEMOids.Biz2, SEMOids.biz2.Track })
public class SEMTrackModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/7/2";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(Issue.class, "issue");
    }

}
