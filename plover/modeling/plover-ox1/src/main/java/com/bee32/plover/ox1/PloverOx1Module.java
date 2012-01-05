package com.bee32.plover.ox1;

import com.bee32.icsf.principal.GroupDao;
import com.bee32.icsf.principal.RoleDao;
import com.bee32.icsf.principal.UserDao;
import com.bee32.plover.orm.util.ERModule;
import com.bee32.plover.pub.oid.Oid;
import com.bee32.plover.pub.oid.PloverOids;

@Oid({ 3, 12, PloverOids.Library, PloverOids.library.ORMExt })
public class PloverOx1Module
        extends ERModule {

    public static final String PREFIX = "/3/12/3/5";
    public static final String PREFIX_ = PREFIX + "/";

    @Override
    protected void preamble() {
        // still, no pages for -ox1 yet.

        // no pages for -principal yet.
        // declareEntityPages(entityType, shortName)
    }

    @Override
    protected void assemble() {
        export(UserDao.class, "user");
        export(GroupDao.class, "group");
        export(RoleDao.class, "role");
        // export(RealmDao.class, "realm");
    }

}
