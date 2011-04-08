package com.bee32.icsf.access.fea1;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.access.IcsfAccessUnit;
import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACEDao;
import com.bee32.icsf.access.alt.R_ACLDao;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.WiredDaoTestCase;
import com.bee32.plover.test.FeaturePlayer;

@Import(WiredDaoTestCase.class)
@Using(IcsfAccessUnit.class)
@Scope("prototype")
@Lazy
@Service
public class PlayACL
        extends FeaturePlayer<PlayACL> {

    /**
     * To inject the sample beans.
     */
    @Inject
    SamplesLoader samplesLoader;

    @Inject
    R_ACLDao aclDao;

    @Inject
    R_ACEDao aceDao;

    @Transactional
    public void listSamples() {
        samplesLoader.loadNormalSamples();

        for (R_ACE ace : aceDao.list()) {
            System.out.println(ace);
        }
    }

    @Override
    protected void main(PlayACL player)
            throws Exception {
        player.listSamples();
    }

    public static void main(String[] args)
            throws IOException {
        new PlayACL().mainLoop();
    }

}
