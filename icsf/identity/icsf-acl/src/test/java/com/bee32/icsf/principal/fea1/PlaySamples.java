package com.bee32.icsf.principal.fea1;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.context.TestDataConfig;
import com.bee32.plover.orm.dao.CommonDataManager;
import com.bee32.plover.orm.dao.MemdbDataManager;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.ox1.principal.AbstractPrincipalDao;
import com.bee32.plover.ox1.principal.Group;
import com.bee32.plover.ox1.principal.GroupDao;
import com.bee32.plover.ox1.principal.Principal;
import com.bee32.plover.ox1.principal.RoleDao;
import com.bee32.plover.ox1.principal.User;
import com.bee32.plover.ox1.principal.UserDao;
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
    AbstractPrincipalDao<? extends Principal> principalDao;

    @Inject
    CommonDataManager dataManager = MemdbDataManager.getInstance();

    <E extends Entity<K>, K extends Serializable> //
    IEntityAccessService<E, K> asFor(Class<E> entityType) {
        IEntityAccessService<E, K> service = dataManager.asFor(entityType);
        return service;
    }

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

        asFor(Principal.class).saveAll(g1, u1, u2);

        u1 = asFor(User.class).merge(u1);
        u2 = asFor(User.class).merge(u2);
        g1 = asFor(Group.class).merge(g1);

        // dataManager.flush();
    }

    @Transactional
    public void run2() {
        Group gr1 = null;
        User ur1 = null;
        User ur2 = null;
        try {
            gr1 = asFor(Group.class).getOrFail(g1.getId());
            ur1 = asFor(User.class).getOrFail(u1.getId());
            ur2 = asFor(User.class).getOrFail(u2.getId());

            assertTrue(ur1.implies(gr1));
            assertFalse(gr1.implies(ur1));

            System.out.println(gr1);
            System.out.println(ur1);
            System.out.println(ur2);

        } finally {
            asFor(User.class).delete(ur1.detach());
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
