package com.bee32.icsf.principal;

import org.apache.commons.codec.digest.DigestUtils;

import com.bee32.icsf.login.PrivateQuestion;
import com.bee32.icsf.login.UserPassword;
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

    public static Group solaRobots = new Group("Sola Robots Club");
    public static Group sunCorp = new Group("Sun Corp");

    public static Role registeredRole = new Role("Registered User");

    public static User eva = new User("Eva", null, Role.adminRole);
    public static User wallE = new User("Wall-E", solaRobots, registeredRole);
    public static User alice = new User("Alice", null, null);
    public static User tom = new User("Tom", null, Role.adminRole);
    public static User kate = new User("Kate", sunCorp, registeredRole);

    static {
        solaRobots.setOwner(eva);
        sunCorp.setOwner(tom);
        eva.setPrimaryGroup(solaRobots);
        tom.setPrimaryGroup(sunCorp);

        alice.addAssignedGroup(sunCorp);
        alice.addAssignedGroup(solaRobots);
        alice.addAssignedRole(registeredRole);

        eva.setPreferredEmail("eva@bee32.com");
        wallE.setPreferredEmail("wall-e@bee32.com");
        tom.setPreferredEmail("tom@bee32.com");
        kate.setPreferredEmail("kate@bee32.com");
        alice.setPreferredEmail("alice@bee32.com");
    }

    @Override
    protected void preamble() {
        addSamples(sunCorp, solaRobots);
        addSamples(Role.adminRole, registeredRole);
        addSamples(User.admin, eva, wallE, alice, tom, kate);
        addSample(User.adminApAll);
        addSample(User.adminEntityAll);

        addSample(PrivateQuestion.DADS_NAME);
        addSample(PrivateQuestion.MOMS_NAME);

        addSample(new UserPassword(User.admin, sha1("Bee32")));
        addSample(new UserPassword(eva, sha1("EVA")));
        addSample(new UserPassword(wallE, sha1("WALL-E")));
        addSample(new UserPassword(alice, sha1("ALICE")));
        addSample(new UserPassword(tom, sha1("TOM")));
        addSample(new UserPassword(kate, sha1("KATE")));
    }

    static String sha1(String text) {
        return DigestUtils.shaHex(text);
    }

}
