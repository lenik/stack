package com.bee32.icsf.principal;

import com.bee32.plover.orm.util.StandardSamples;

public class Roles
        extends StandardSamples {

    public final Role adminRole = new Role("adminRole", "Administrator Users");
    public final Role powerUserRole = new Role("powerUserRole", "Powerful Users");
    public final Role userRole = new Role("userRole", "Registered Users");
    public final Role guestRole = new Role("guestRole", "Guest Users");

    @Override
    protected void assemble() {
        powerUserRole.setInheritedRole(userRole);
    }

}
