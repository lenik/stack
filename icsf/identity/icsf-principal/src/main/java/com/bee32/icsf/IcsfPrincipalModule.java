package com.bee32.icsf;

import javax.inject.Inject;

import com.bee32.icsf.principal.dao.GroupDao;
import com.bee32.icsf.principal.dao.PrincipalDao;
import com.bee32.icsf.principal.dao.RealmDao;
import com.bee32.icsf.principal.dao.RoleDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

@Oid({ 3, 7, IcsfOids.principal })
public class IcsfPrincipalModule
        extends ERModule {

    @Inject
    PrincipalDao principalDao;

    @Inject
    UserDao userDao;

    @Inject
    GroupDao groupDao;

    @Inject
    RoleDao roleDao;

    @Inject
    RealmDao realmDao;

    @Override
    protected void preamble() {
        export(principalDao);
        export(userDao);
        export(groupDao);
        export(roleDao);
        export(realmDao);
    }

}
