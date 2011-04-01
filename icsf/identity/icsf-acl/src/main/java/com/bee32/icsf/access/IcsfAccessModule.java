package com.bee32.icsf.access;

import com.bee32.icsf.IcsfOids;
import com.bee32.icsf.access.alt.R_ACEDao;
import com.bee32.icsf.access.alt.R_ACLDao;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

@Oid({ 3, 7, IcsfOids.Acl })
public class IcsfAccessModule
        extends ERModule {

    @Override
    protected void preamble() {
        export(R_ACLDao.class, "acl");
        export(R_ACEDao.class, "ace");
    }

}
