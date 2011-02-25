package com.bee32.sem.process;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.process, SEMOids.processProcess })
public class SemProcessModule
        extends EnterpriseModule {

    @Override
    protected void preamble() {
        // exportEntity(VerifyPolicy.class, String.class);
    }

}
