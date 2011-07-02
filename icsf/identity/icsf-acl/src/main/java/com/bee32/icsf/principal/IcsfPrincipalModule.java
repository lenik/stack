package com.bee32.icsf.principal;

import com.bee32.icsf.IcsfOids;
import com.bee32.icsf.principal.dao.GroupDao;
import com.bee32.icsf.principal.dao.RealmDao;
import com.bee32.icsf.principal.dao.RoleDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

@Oid({ 3, 7, IcsfOids.Principal })
public class IcsfPrincipalModule
        extends ERModule {

    @Override
    protected void preamble() {
        export(UserDao.class, "user");
        export(GroupDao.class, "group");
        export(RoleDao.class, "role");
        export(RealmDao.class, "realm");
    }

}
