package com.bee32.sem.process;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Process, SEMOids.process.Process })
public class SEMProcessModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/2/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        // export(AllowListDao.class);
        // export(MultiLevelDao.class);
        // export(PassToNextDao.class);
        // export(VerifyPolicyPrefDao.class);
    }

}
