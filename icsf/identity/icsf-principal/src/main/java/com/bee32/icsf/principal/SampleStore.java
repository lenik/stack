package com.bee32.icsf.principal;

import java.util.Map;

import com.bee32.plover.arch.Component;

public class SampleStore {

    public Group sunCorp;
    public Group solaGroup;

    public Role adminRole;
    public Role registeredRole;

    public User eva;
    public User wallE;
    public User alice;
    public User tom;
    public User kate;

    {
        adminRole = new Role("Administrator");
        registeredRole = new Role("Registered User");

        eva = new User("Eva", null, adminRole);
        solaGroup = new Group("Sola AV Club", eva);
        eva.setPrimaryGroup(solaGroup);
        wallE = new User("Wall-E", solaGroup, registeredRole);

        tom = new User("Tom", null, adminRole);
        sunCorp = new Group("Sun Corp", tom);
        tom.setPrimaryGroup(sunCorp);

        kate = new User("Kate", sunCorp, registeredRole);

        alice = new User("Alice", null, null);
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
