package com.bee32.icsf.principal.test;

import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.Role;
import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.util.EntitySamplesContribution;

public class PrincipalSamples
        extends EntitySamplesContribution {

    public Group sunCorp;
    public Group solaGroup;

    public Role adminRole;
    public Role registeredRole;

    public User eva;
    public User wallE;
    public User alice;
    public User tom;
    public User kate;

    public PrincipalSamples() {
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

    @Override
    protected void preamble() {
        addNormalSample(sunCorp, solaGroup);
        addNormalSample(adminRole, registeredRole);
        addNormalSample(eva, wallE, alice, tom, kate);
    }

    static final PrincipalSamples instance = new PrincipalSamples();

    public static PrincipalSamples getInstance() {
        return instance;
    }

}
