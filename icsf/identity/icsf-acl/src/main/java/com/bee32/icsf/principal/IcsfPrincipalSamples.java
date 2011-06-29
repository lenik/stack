package com.bee32.icsf.principal;

import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.login.UserPassword;
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

    public static Group solaRobots = new Group("Sola Robots Club");
    public static Group sunCorp = new Group("Sun Corp");

    public static Role adminRole = new Role("Administrator");

    public static Role registeredRole = new Role("Registered User");

    public static User admin = new User("admin", null, adminRole);
    public static User eva = new User("Eva", null, adminRole);
    public static User wallE = new User("Wall-E", solaRobots, registeredRole);
    public static User alice = new User("Alice", null, null);
    public static User tom = new User("Tom", null, adminRole);
    public static User kate = new User("Kate", sunCorp, registeredRole);

    public static R_ACE adminApAll = new R_ACE("ap:", admin, "Srwx");

    static {
        solaRobots.setOwner(eva);
        sunCorp.setOwner(tom);
        eva.setPrimaryGroup(solaRobots);
        tom.setPrimaryGroup(sunCorp);

        alice.addAssignedGroup(sunCorp);
        alice.addAssignedGroup(solaRobots);
        alice.addAssignedRole(registeredRole);

        eva.setEmailAddress("eva@bee32.com");
        wallE.setEmailAddress("wall-e@bee32.com");
        tom.setEmailAddress("tom@bee32.com");
        kate.setEmailAddress("kate@bee32.com");
        alice.setEmailAddress("alice@bee32.com");
    }

    @Override
    protected void preamble() {
        addNormalSample(sunCorp, solaRobots);
        addNormalSample(adminRole, registeredRole);
        addNormalSample(admin, eva, wallE, alice, tom, kate);
        addNormalSample(adminApAll);

        addNormalSample(new UserPassword(admin, "Bee32"));
        addNormalSample(new UserPassword(eva, "EVA"));
        addNormalSample(new UserPassword(wallE, "WALL-E"));
        addNormalSample(new UserPassword(tom, "TOM"));
        addNormalSample(new UserPassword(alice, "ALICE"));
        addNormalSample(new UserPassword(kate, "KATE"));
    }

}
