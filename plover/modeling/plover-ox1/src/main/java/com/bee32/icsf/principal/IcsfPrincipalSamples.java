package com.bee32.icsf.principal;

import javax.inject.Inject;

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

    public final User eva = new User("Eva");
    public final User wallE = new User("Wall-E");
    public final User alice = new User("Alice");
    public final User tom = new User("Tom");
    public final User kate = new User("Kate");

    @Inject
    Roles roles;

    @Override
    protected void assemble() {
        eva.setPrimaryGroup(solaRobots);
        eva.setPrimaryRole(roles.adminRole);
        wallE.setPrimaryGroup(solaRobots);
        wallE.setPrimaryRole(roles.powerUserRole);
        tom.setPrimaryGroup(sunCorp);
        tom.setPrimaryRole(roles.adminRole);
        kate.setPrimaryGroup(sunCorp);
        kate.setPrimaryRole(roles.userRole);

        alice.addAssignedGroup(sunCorp);
        alice.addAssignedGroup(solaRobots);
        alice.addAssignedRole(roles.userRole);

        eva.setPreferredEmail("eva@bee32.com");
        wallE.setPreferredEmail("wall-e@bee32.com");
        tom.setPreferredEmail("tom@bee32.com");
        kate.setPreferredEmail("kate@bee32.com");
        alice.setPreferredEmail("alice@bee32.com");
    }

    @Override
    protected void listSamples() {
        addBulk(sunCorp, solaRobots);
        addBulk(eva, wallE, alice, tom, kate);
    }

    @Override
    protected void postSave() {
        solaRobots.setOwner(eva);
        sunCorp.setOwner(tom);
        ctx.data.access(Group.class).saveOrUpdateAll(solaRobots, sunCorp);
    }

}
