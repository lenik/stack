package com.bee32.sem.opportunity;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.opportunity.dao.CompetitorDao;
import com.bee32.sem.opportunity.dao.SalesChanceDao;
import com.bee32.sem.opportunity.dao.SalesChanceDetailDao;
import com.bee32.sem.opportunity.dao.SalesChanceHistoryDao;

@Oid({ 3, 15, SEMOids.Base, 100 })
public class SEMOpportunityModule
        extends EnterpriseModule {

    @Override
    protected void preamble() {
        export(SalesChanceHistoryDao.class, "history");
        export(SalesChanceDetailDao.class, "assoc");
        export(CompetitorDao.class, "competitor");
        export(SalesChanceDao.class, "chance");
    }

}
