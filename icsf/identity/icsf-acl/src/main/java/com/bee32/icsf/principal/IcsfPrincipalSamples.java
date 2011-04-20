package com.bee32.icsf.principal;

import com.bee32.plover.orm.util.EntitySamplesContribution;

/**
 * Sample principals:
 *
 * <pre>
 * sola(eva*, wallE, alice)
 * sun(tom*, kate, alice)
 * </pre>
 */
public class IcsfPrincipalSamples
        extends EntitySamplesContribution {

    public static Group solaGroup;

    public static Group sunCorp;

    public static Role adminRole;
    public static Role registeredRole;

    public static User eva;
    public static User wallE;
    public static User alice;
    public static User tom;
    public static User kate;

    static {
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

        eva.setEmail("eva@bee32.com");
        wallE.setEmail("wall-e@bee32.com");
        tom.setEmail("tom@bee32.com");
        kate.setEmail("kate@bee32.com");
        alice.setEmail("alice@bee32.com");
    }

    @Override
    protected void preamble() {
        addNormalSample(sunCorp, solaGroup);
        addNormalSample(adminRole, registeredRole);
        addNormalSample(eva, wallE, alice, tom, kate);
    }

}
