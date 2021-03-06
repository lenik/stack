package com.bee32.icsf.access.alt;

import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.sample.StandardSamples;

public class R_ACEs
        extends StandardSamples {

    public final R_ACE adminApAll = new R_ACE();
    public final R_ACE adminEntityAll = new R_ACE();

    Users users = predefined(Users.class);

    @Override
    protected void wireUp() {
        adminApAll.init("ap:", users.admin, "surwcdx");
        adminEntityAll.init("entity:", users.admin, "surwcdx");
    }

}
