package com.bee32.icsf.principal;

import com.bee32.icsf.IcsfOids;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.ox1.principal.GroupDao;
import com.bee32.plover.ox1.principal.RoleDao;
import com.bee32.plover.ox1.principal.UserDao;
import com.bee32.plover.pub.oid.Oid;

@Oid({ 3, 7, IcsfOids.Principal })
public class IcsfPrincipalModule
        extends ERModule {

    public static final String PREFIX = "/3/7/1";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        export(UserDao.class, "user");
        export(GroupDao.class, "group");
        export(RoleDao.class, "role");
        // export(RealmDao.class, "realm");
    }

}
