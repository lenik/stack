package com.bee32.sem.chance;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.chance.dao.ChanceActionDao;
import com.bee32.sem.chance.dao.ChanceDao;
import com.bee32.sem.chance.dao.ChanceStageDao;
import com.bee32.sem.chance.dao.CompetitorDao;
import com.bee32.sem.module.EnterpriseModule;

@Oid({ 3, 15, SEMOids.Base, 100 })
public class SEMChanceModule
        extends EnterpriseModule {

    @Override
    protected void preamble() {
        export(ChanceActionDao.class, "history");
        export(ChanceStageDao.class, "assoc");
        export(CompetitorDao.class, "competitor");
        export(ChanceDao.class, "chance");
    }

}
