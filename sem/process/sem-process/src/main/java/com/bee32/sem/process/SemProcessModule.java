package com.bee32.sem.process;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.process.verify.builtin.dao.AllowListDao;
import com.bee32.sem.process.verify.builtin.dao.MultiLevelAllowListDao;
import com.bee32.sem.process.verify.builtin.dao.PassToNextDao;

@Oid({ 3, 15, SEMOids.process, SEMOids.processProcess })
public class SemProcessModule
        extends EnterpriseModule {

    @Override
    protected void preamble() {
        export(new AllowListDao(), "alist");
        export(new MultiLevelAllowListDao(), "mlist");
        export(new PassToNextDao(), "p2next");
    }

}
