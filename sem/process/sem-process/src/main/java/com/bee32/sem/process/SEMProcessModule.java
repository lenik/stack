package com.bee32.sem.process;

import com.bee32.plover.pub.oid.Oid;
import com.bee32.sem.SEMOids;
import com.bee32.sem.module.EnterpriseModule;
import com.bee32.sem.process.verify.builtin.dao.AllowListDao;
import com.bee32.sem.process.verify.builtin.dao.MultiLevelDao;
import com.bee32.sem.process.verify.builtin.dao.PassToNextDao;
import com.bee32.sem.process.verify.typedef.VerifyPolicyPrefDao;

@Oid({ 3, 15, SEMOids.Process, SEMOids.process.Process })
public class SEMProcessModule
        extends EnterpriseModule {

    public static final String PREFIX = "/3/15/2/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        export(AllowListDao.class);
        export(MultiLevelDao.class);
        export(PassToNextDao.class);
        export(VerifyPolicyPrefDao.class);
    }

}
