package com.bee32.sem.chance;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Base, 100 })
public class SEMChanceModule
        extends EnterpriseModule {

    @Override
    protected void preamble() {
        export(SalesChanceHistoryDao.class, "history");
        export(SalesChanceDetailDao.class, "assoc");
        export(CompetitorDao.class, "competitor");
        export(SalesChanceDao.class, "chance");
    }

}
