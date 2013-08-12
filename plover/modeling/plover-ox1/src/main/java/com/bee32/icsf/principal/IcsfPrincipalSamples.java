package com.bee32.icsf.principal;

import com.bee32.plover.orm.sample.NormalSamples;
import com.bee32.plover.orm.util.DataPartialContext;

/**
 * ICSF Principal Samples
 *
 * Sample users, groups, roles.
 *
 * <pre>
 * sola(eva*, wallE, alice)
 * sun(tom*, kate, alice)
 * </pre>
 */
public class IcsfPrincipalSamples
        extends NormalSamples {

    /**
     * <p lang="zh-cn">
     * 索拉机器人同盟
     */
    public final Group solaRobots = new Group("sola", PREFIX + "Sola 机器人俱乐部");
    /**
     * <p lang="zh-cn">
     * 小日有限公司
     */
    public final Group sunCorp = new Group("sun-corp", PREFIX + "太阳微电子公司");

    /**
     * <p lang="zh-cn">
     * 伊娃
     */
    public final User eva = new User("Eva", PREFIX + "伊娃");

    /**
     * <p lang="zh-cn">
     * 瓦利"
     */
    public final User wallE = new User("Wall-E", PREFIX + "瓦利");

    /**
     * <p lang="zh-cn">
     * 爱丽丝
     */
    public final User alice = new User("Alice", PREFIX + "爱丽丝");

    /**
     * <p lang="zh-cn">
     * 汤姆
     */
    public final User tom = new User("Tom", PREFIX + "汤姆");

    /**
     * <p lang="zh-cn">
     * 凯特
     */
    public final User kate = new User("Kate", PREFIX + "凯特");

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

        // Workaround: duplicated keys.
        // alice.addAssignedGroup(sunCorp);
        // alice.addAssignedGroup(solaRobots);
        // alice.addAssignedRole(users.userRole);
        sunCorp.addMemberUser(alice, false);
        solaRobots.addMemberUser(alice, false);
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

    @Override
    public void beginLoad() {
    }

}
