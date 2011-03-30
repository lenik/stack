package com.bee32.icsf.access;

import javax.inject.Inject;

import com.bee32.icsf.IcsfOids;
import com.bee32.icsf.access.alt.R_ACEDao;
import com.bee32.icsf.access.alt.R_ACLDao;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

@Oid({ 3, 7, IcsfOids.Acl })
public class IcsfAccessModule
        extends ERModule {

    @Inject
    R_ACLDao r_ACLDao;

    @Inject
    R_ACEDao r_ACEDao;

    @Override
    protected void preamble() {
        export(r_ACLDao);
        export(r_ACEDao);
    }

}
