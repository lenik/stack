package com.bee32.icsf;

import com.bee32.icsf.principal.GroupBean;
import com.bee32.icsf.principal.RoleBean;
import com.bee32.icsf.principal.UserBean;
import com.bee32.icsf.principal.realm.RealmBean;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

// XXX Fix the oid.
@Oid({ 3, 7, IcsfOids.principal })
public class IcsfPrincipalModule
        extends ERModule {

    @Override
    protected void preamble() {
        exportEntity(UserBean.class, Long.class);
        exportEntity(GroupBean.class, Integer.class);
        exportEntity(RoleBean.class, Integer.class);
        exportEntity(RealmBean.class, Integer.class);
    }

}
