package com.bee32.icsf.principal;

public class SamplePrincipals {

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

    static final SamplePrincipals instance = new SamplePrincipals();

    public static SamplePrincipals getInstance() {
        return instance;
    }

}
