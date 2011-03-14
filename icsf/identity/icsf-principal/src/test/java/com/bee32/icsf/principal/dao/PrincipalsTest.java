package com.bee32.icsf.principal.dao;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.User;
import com.bee32.plover.arch.SpringModuleLoader;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Transactional
public class PrincipalsTest
        extends WiredDaoTestCase {

    /**
     * To inject the sample beans.
     */
    @Inject
    SpringModuleLoader loader;

    @Inject
    UserDao userDao;

    @Inject
    GroupDao groupDao;

    @Inject
    RoleDao roleDao;

    @Before
    public void setUp() {
        loader.load();
    }

    @Test
    public void testWithSamples() {
        loadSamples();
    }

    @Transactional
    void loadSamples() {
        for (User user : userDao.list())
            System.out.println("Sample User: " + user);
    }

}
