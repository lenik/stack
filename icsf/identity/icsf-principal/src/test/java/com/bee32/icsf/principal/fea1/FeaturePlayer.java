package com.bee32.icsf.principal.fea1;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dao.GroupDao;
import com.bee32.icsf.principal.dao.RoleDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.icsf.principal.test.PrincipalSamples;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;
import com.bee32.plover.inject.spring.ContextConfiguration;
import com.bee32.plover.orm.context.TestDataConfig;
import com.bee32.plover.orm.util.SamplesLoader;

@Import({ ScanTestxContext.class, TestDataConfig.class })
@ContextConfiguration("context.xml")
@Service
@Scope("prototype")
public class FeaturePlayer {

    static Logger logger = LoggerFactory.getLogger(FeaturePlayer.class);

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

    @Transactional
    public void listSamples() {
        samplesLoader.loadNormalSamples();

        for (User user : userDao.list())
            System.out.println("Sample User: " + user);

        System.out.println("Alicd.id = " + PrincipalSamples.alice.getId());
    }

}
