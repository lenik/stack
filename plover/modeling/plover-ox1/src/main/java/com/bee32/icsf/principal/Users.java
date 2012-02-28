package com.bee32.icsf.principal;

import com.bee32.plover.orm.util.StandardSamples;

public class Users
        extends StandardSamples {

    public final User admin = new User("admin");
    public final User guest = new User("guest");

    public final Role adminRole = new Role("adminRole", "Administrator Users");
    public final Role powerUserRole = new Role("powerUserRole", "Powerful Users");
    public final Role userRole = new Role("userRole", "Registered Users");
    public final Role guestRole = new Role("guestRole", "Guest Users");

    @Override
    public int getPriority() {
        return PRIORITY_SYSTEM;
    }

    @Override
    protected void wireUp() {
        admin.setPrimaryRole(adminRole);
        powerUserRole.setInheritedRole(userRole);
    }

}
