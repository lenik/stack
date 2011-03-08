package com.bee32.icsf;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;

import com.bee32.icsf.principal.dao.GroupDao;
import com.bee32.icsf.principal.dao.RealmDao;
import com.bee32.icsf.principal.dao.RoleDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;

@Configuration
@Oid({ 3, 7, IcsfOids.principal })
public class IcsfPrincipalModule
        extends ERModule {

    @Inject
    private UserDao userDao;

    @Inject
    private GroupDao groupDao;

    @Inject
    private RoleDao roleDao;

    @Inject
    private RealmDao realmDao;

    @Override
    protected void preamble() {
        export(userDao);
        export(groupDao);
        export(roleDao);
        export(realmDao);
    }

}
