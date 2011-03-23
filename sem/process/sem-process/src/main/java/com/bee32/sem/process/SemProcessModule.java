package com.bee32.sem.process;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.process.verify.builtin.dao.AllowListDao;
import com.bee32.sem.process.verify.builtin.dao.MultiLevelDao;
import com.bee32.sem.process.verify.builtin.dao.PassToNextDao;

@Oid({ 3, 15, SEMOids.Process, SEMOids.process.Process })
public class SemProcessModule
        extends EnterpriseModule {

    @Override
    protected void preamble() {
        export(new AllowListDao(), "list");
        export(new MultiLevelDao(), "level");
        export(new PassToNextDao(), "p2next");
    }

}
