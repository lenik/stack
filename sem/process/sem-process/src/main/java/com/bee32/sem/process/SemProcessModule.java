package com.bee32.sem.process;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.process.verify.impl.AllowListRepo;
import com.bee32.sem.process.verify.impl.MultiLevelAllowListRepo;
import com.bee32.sem.process.verify.impl.PassToNextRepo;

@Oid({ 3, 15, SEMOids.process, SEMOids.processProcess })
public class SemProcessModule
        extends EnterpriseModule {

    @Override
    protected void preamble() {
        export(new AllowListRepo(), "alist");
        export(new MultiLevelAllowListRepo(), "mlist");
        export(new PassToNextRepo(), "pseq");
    }

}
