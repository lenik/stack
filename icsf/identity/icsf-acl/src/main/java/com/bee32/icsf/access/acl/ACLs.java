package com.bee32.icsf.access.acl;

import com.bee32.icsf.access.Permission;
import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.entity.EntityAccessor;
import com.bee32.plover.orm.entity.EntityFlags;
import com.bee32.plover.orm.util.StandardSamples;

public class ACLs
        extends StandardSamples {

    public final ACL DEFAULT = new ACL();

    Users users = predefined(Users.class);

    @Override
    protected void wireUp() {
        DEFAULT.setLabel("一般安全策略");
        DEFAULT.add(users.adminRole, Permission.RWS);
        DEFAULT.add(users.powerUserRole, Permission.R_X);
        DEFAULT.add(users.userRole, Permission.R_X);
        DEFAULT.add(users.guestRole, Permission.R_X);
        EntityAccessor.putFlags(EntityFlags.WEAK_DATA, DEFAULT);
    }

}
