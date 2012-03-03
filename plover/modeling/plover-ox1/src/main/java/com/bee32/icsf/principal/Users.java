package com.bee32.icsf.principal;

import com.bee32.plover.orm.util.StandardSamples;

public class Users
        extends StandardSamples {

    public Role adminRole = new Role("adminRole", "Administrator Users");
    public Role powerUserRole = new Role("powerUserRole", "Powerful Users");
    public Role userRole = new Role("userRole", "Registered Users");
    public Role guestRole = new Role("guestRole", "Guest Users");

    public User admin = new User("admin");
    public User guest = new User("guest");

    @Override
    public int getPriority() {
        return PRIORITY_SYSTEM;
    }

    @Override
    protected void wireUp() {
        admin.setPrimaryRole(adminRole);
        powerUserRole.setInheritedRole(userRole);
    }

    @Override
    public void beginLoad() {
    }

}
