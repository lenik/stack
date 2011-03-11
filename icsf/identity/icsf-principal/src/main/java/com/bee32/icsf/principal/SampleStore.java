package com.bee32.icsf.principal;

import java.util.Map;

import com.bee32.plover.arch.Component;

public class SampleStore {

    public GroupBean sunCorp;
    public GroupBean solaGroup;

    public RoleBean adminRole;
    public RoleBean registeredRole;

    public UserBean eva;
    public UserBean wallE;
    public UserBean alice;
    public UserBean tom;
    public UserBean kate;

    {
        adminRole = new RoleBean("Administrator");
        registeredRole = new RoleBean("Registered User");

        eva = new UserBean("Eva", null, adminRole);
        solaGroup = new GroupBean("Sola AV Club", eva);
        eva.setPrimaryGroup(solaGroup);
        wallE = new UserBean("Wall-E", solaGroup, registeredRole);

        tom = new UserBean("Tom", null, adminRole);
        sunCorp = new GroupBean("Sun Corp", tom);
        tom.setPrimaryGroup(sunCorp);

        kate = new UserBean("Kate", sunCorp, registeredRole);

        alice = new UserBean("Alice", null, null);
        alice.addAssignedGroup(sunCorp);
        alice.addAssignedGroup(solaGroup);
        alice.addAssignedRole(registeredRole);
    }

    /**
     * Who git it to me?
     */
    static protected Map<String, Component> type;

    static final SampleStore instance = new SampleStore();

    public static SampleStore getInstance() {
        return instance;
    }

}
