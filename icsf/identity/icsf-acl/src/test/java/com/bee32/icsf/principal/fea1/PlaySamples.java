package com.bee32.icsf.principal.fea1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.icsf.principal.Group;
import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dao.GroupDao;
import com.bee32.icsf.principal.dao.PrincipalDao;
import com.bee32.icsf.principal.dao.RoleDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.context.TestDataConfig;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.test.FeaturePlayer;

@Using(IcsfIdentityUnit.class)
@Service
@Scope("prototype")
@Import(TestDataConfig.class)
@Transactional(readOnly = true)
public class PlaySamples
        extends FeaturePlayer<PlaySamples> {

    static Logger logger = LoggerFactory.getLogger(PlaySamples.class);

    /**
     * To inject the sample beans.
     */
    @Inject
    SamplesLoader samplesLoader;

    @Inject
    UserDao userDao;

    @Inject
    GroupDao groupDao;

    @Inject
    RoleDao roleDao;

    @Inject
    PrincipalDao principalDao;

    @Inject
    CommonDataManager dataManager;

    @Transactional
    public void listSamples() {
        samplesLoader.loadNormalSamples();

        for (User user : userDao.list())
            System.out.println("Sample User: " + user);

        System.out.println("Alice.id = " + IcsfPrincipalSamples.alice.getId());
    }

    Group g1 = new Group("g1");
    User u1 = new User("u1");
    User u2 = new User("u2");

    @Transactional
    public void run0() {
        List<Principal> all = new ArrayList<Principal>();

        for (Principal p : principalDao.list())
            all.add(p.detach());

        for (Principal p : all)
            principalDao.delete(p);
    }

     @Transactional
    public void run1() {
        // g1.setOwner(u1);
        u1.addAssignedGroup(g1);
        u2.addAssignedGroup(g1);

        dataManager.saveAll(g1, u1, u2);

        u1 = dataManager.merge(u1);
        u2 = dataManager.merge(u2);
        g1 = dataManager.merge(g1);

        // dataManager.flush();
    }

    @Transactional
    public void run2() {
        Group gr1 = null;
        User ur1 = null;
        User ur2 = null;
        try {
            gr1 = dataManager.fetch(Group.class, g1.getId());
            ur1 = dataManager.fetch(User.class, u1.getId());
            ur2 = dataManager.fetch(User.class, u2.getId());

            assertTrue(ur1.implies(gr1));
            assertFalse(gr1.implies(ur1));

            System.out.println(gr1);
            System.out.println(ur1);
            System.out.println(ur2);

        } finally {
            dataManager.delete(ur1.detach());
        }
    }

    @Override
    protected void main(PlaySamples player)
            throws Exception {
        // player.listSamples();

        try {
            player.run0();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            player.run1();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            player.run2();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args)
            throws IOException {
        new PlaySamples().mainLoop();
    }

}
