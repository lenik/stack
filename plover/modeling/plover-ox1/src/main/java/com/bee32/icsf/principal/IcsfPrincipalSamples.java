package com.bee32.icsf.principal;

import com.bee32.plover.orm.util.DataPartialContext;
import com.bee32.plover.orm.util.NormalSamples;

/**
 * Sample principals:
 *
 * <pre>
 * sola(eva*, wallE, alice)
 * sun(tom*, kate, alice)
 * </pre>
 */
public class IcsfPrincipalSamples
        extends NormalSamples {

    public Group solaRobots = new Group("sola", PREFIX + "Sola Robots Club");
    public Group sunCorp = new Group("sun-corp", PREFIX + "Sun Corp");

    public User eva = new User("Eva");
    public User wallE = new User("Wall-E");
    public User alice = new User("Alice");
    public User tom = new User("Tom");
    public User kate = new User("Kate");

    Users users = predefined(Users.class);

    @Override
    protected void wireUp() {
        eva.setPrimaryGroup(solaRobots);
        eva.setPrimaryRole(users.adminRole);
        wallE.setPrimaryGroup(solaRobots);
        wallE.setPrimaryRole(users.powerUserRole);
        tom.setPrimaryGroup(sunCorp);
        tom.setPrimaryRole(users.adminRole);
        kate.setPrimaryGroup(sunCorp);
        kate.setPrimaryRole(users.userRole);

        alice.addAssignedGroup(sunCorp);
        alice.addAssignedGroup(solaRobots);
        alice.addAssignedRole(users.userRole);

        eva.setPreferredEmail("eva@bee32.com");
        wallE.setPreferredEmail("wall-e@bee32.com");
        tom.setPreferredEmail("tom@bee32.com");
        kate.setPreferredEmail("kate@bee32.com");
        alice.setPreferredEmail("alice@bee32.com");
    }

    @Override
    protected void postSave(DataPartialContext data) {
        solaRobots.setOwner(eva);
        sunCorp.setOwner(tom);
        data.access(Group.class).saveOrUpdateAll(solaRobots, sunCorp);
    }

}
