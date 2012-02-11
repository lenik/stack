package com.bee32.icsf.principal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;

@Using(IcsfPrincipalUnit.class)
public class UsersFeat
        extends WiredDaoFeat<UsersFeat> {

    static Logger logger = LoggerFactory.getLogger(UsersFeat.class);

    @Inject
    UserDao userDao;

    @Inject
    GroupDao groupDao;

    @Inject
    RoleDao roleDao;

    @Inject
    AbstractPrincipalDao<? extends Principal> principalDao;

    @Inject
    CommonDataManager dataManager = MemdbDataManager.getInstance();

    @Transactional(readOnly = true)
    public void listSamples() {
        for (User user : userDao.list())
            System.out.println("Sample User: " + user);

        System.out.println("Alice.id = " + IcsfPrincipalSamples.alice.getId());
    }

    Group g1 = new Group("g1");
    User u1 = new User("u1");
    User u2 = new User("u2");

    @Transactional
    public void run_0() {
        List<Principal> all = new ArrayList<Principal>();

        for (Principal p : principalDao.list())
            all.add(p.detach());

        for (Principal p : all)
            principalDao.delete(p);
    }

    @SuppressWarnings("deprecation")
    @Transactional
    public void run_1() {
        // g1.setOwner(u1);
        u1.addAssignedGroup(g1);
        u2.addAssignedGroup(g1);

        ctx.data.access(Principal.class).saveAll(g1, u1, u2);

        u1 = ctx.data.access(User.class).merge(u1);
        u2 = ctx.data.access(User.class).merge(u2);
        g1 = ctx.data.access(Group.class).merge(g1);

        // dataManager.flush();
    }

    @Transactional
    public void run_2() {
        Group gr1 = null;
        User ur1 = null;
        User ur2 = null;
        try {
            gr1 = ctx.data.access(Group.class).getOrFail(g1.getId());
            ur1 = ctx.data.access(User.class).getOrFail(u1.getId());
            ur2 = ctx.data.access(User.class).getOrFail(u2.getId());

            assertTrue(ur1.implies(gr1));
            assertFalse(gr1.implies(ur1));

            System.out.println(gr1);
            System.out.println(ur1);
            System.out.println(ur2);

        } finally {
            ctx.data.access(User.class).delete(ur1.detach());
        }
    }

    public static void main(String[] args)
            throws IOException {
        new UsersFeat().mainLoop(new ICoordinator<UsersFeat>() {
            @Override
            public void main(UsersFeat feat)
                    throws Exception {

                // feat.listSamples();

                try {
                    feat.run_0();
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                try {
                    feat.run_1();
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                try {
                    feat.run_2();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
