package com.bee32.icsf.principal.fea1;

import java.io.IOException;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.User;
import com.bee32.icsf.principal.dao.GroupDao;
import com.bee32.icsf.principal.dao.RoleDao;
import com.bee32.icsf.principal.dao.UserDao;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;
import com.bee32.plover.orm.context.TestDataConfig;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.test.FeaturePlayer;

@Using(IcsfIdentityUnit.class)
@Import({ ScanTestxContext.class, TestDataConfig.class })
@Scope("prototype")
@Service
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

    @Transactional
    public void listSamples() {
        samplesLoader.loadNormalSamples();

        for (User user : userDao.list())
            System.out.println("Sample User: " + user);

        System.out.println("Alice.id = " + IcsfPrincipalSamples.alice.getId());
    }

    @Override
    protected void main(PlaySamples player)
            throws Exception {
        player.listSamples();
    }

    public static void main(String[] args)
            throws IOException {
        new PlaySamples().mainLoop();
    }

}
