package com.bee32.icsf.principal;

import javax.inject.Inject;

import com.bee32.plover.orm.util.StandardSamples;

public class Users
        extends StandardSamples {

    public final User admin = new User("admin");
    public final User guest = new User("guest");

    @Inject
    Roles roles;

    @Override
    protected void preamble() {
        admin.setPrimaryRole(roles.adminRole);
    }

}
