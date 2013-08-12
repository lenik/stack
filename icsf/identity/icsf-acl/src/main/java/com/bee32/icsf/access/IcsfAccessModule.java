package com.bee32.icsf.access;

import com.bee32.icsf.IcsfOids;
import com.bee32.icsf.access.alt.R_ACEDao;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

/**
 * 访问安全模块
 *
 * ICSF安全框架中用语控制访问安全的模块。
 *
 * <p lang="en">
 * ICSF Access Module
 */
@Oid({ 3, 7, IcsfOids.Acl })
public class IcsfAccessModule
        extends ERModule {

    public static final String PREFIX = "/3/7/2";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        declareEntityPages(User.class, "user");
    }

    @Override
    protected void assemble() {
        export(R_ACEDao.class, "entry");
    }

}
