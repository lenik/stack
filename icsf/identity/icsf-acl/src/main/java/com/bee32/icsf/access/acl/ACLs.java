package com.bee32.icsf.access.acl;

import javax.inject.Inject;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Roles;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.util.StandardSamples;

public class ACLs
        extends StandardSamples {

    public final ACL DEFAULT = new ACL();

    @Inject
    Roles roles;

    @Override
    protected void assemble() {
        DEFAULT.setName("default");
        DEFAULT.setLabel("一般安全策略");
        DEFAULT.add(roles.adminRole, Permission.RWS);
        DEFAULT.add(roles.powerUserRole, Permission.R_X);
        DEFAULT.add(roles.userRole, Permission.R_X);
        DEFAULT.add(roles.guestRole, Permission.R_X);
        EntityFlags ef = EntityAccessor.getFlags(DEFAULT);
        ef.setWeakData(true);
    }

}
