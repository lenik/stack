package com.bee32.icsf.principal.fea1;

import javax.inject.Inject;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dao.GroupDao;
import com.bee32.icsf.principal.dao.RoleDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.arch.SpringModuleLoader;

@Scope("prototype")
@Service
public class FeaturePlayer {

    static Logger logger = LoggerFactory.getLogger(FeaturePlayer.class);

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

    @Transactional
    public void listSamples() {
        for (User user : userDao.list())
            System.out.println("Sample User: " + user);
    }

}
