package com.bee32.icsf.principal;

import com.bee32.plover.orm.util.SampleContribution;

/**
 * Sample principals:
 *
 * <pre>
 * sola(eva*, wallE, alice)
 * sun(tom*, kate, alice)
 * </pre>
 */
public class IcsfPrincipalSamples
        extends SampleContribution {

    public final Group solaRobots = new Group("sola", "Sola Robots Club");
    public final Group sunCorp = new Group("sun", "Sun Corp");

    public final User eva = new User("Eva", null, Role.adminRole);
    public final User wallE = new User("Wall-E", solaRobots, Role.powerUserRole);
    public final User alice = new User("Alice", null, null);
    public final User tom = new User("Tom", null, Role.adminRole);
    public final User kate = new User("Kate", sunCorp, Role.userRole);

    @Override
    protected void preamble() {
        eva.setPrimaryGroup(solaRobots);
        tom.setPrimaryGroup(sunCorp);

        alice.addAssignedGroup(sunCorp);
        alice.addAssignedGroup(solaRobots);
        alice.addAssignedRole(Role.userRole);

        eva.setPreferredEmail("eva@bee32.com");
        wallE.setPreferredEmail("wall-e@bee32.com");
        tom.setPreferredEmail("tom@bee32.com");
        kate.setPreferredEmail("kate@bee32.com");
        alice.setPreferredEmail("alice@bee32.com");

        addBulk(sunCorp, solaRobots);
        addBulk(eva, wallE, alice, tom, kate);
    }

    @Override
    protected void more() {
        solaRobots.setOwner(eva);
        sunCorp.setOwner(tom);
        ctx.data.access(Group.class).saveOrUpdateAll(solaRobots, sunCorp);
    }

}
