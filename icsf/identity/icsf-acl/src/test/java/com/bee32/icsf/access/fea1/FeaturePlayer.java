package com.bee32.icsf.access.fea1;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACEDao;
import com.bee32.icsf.access.alt.R_ACLDao;
import com.bee32.plover.orm.util.SamplesLoader;

@Scope("prototype")
@Service
public class FeaturePlayer {

    static Logger logger = LoggerFactory.getLogger(FeaturePlayer.class);

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

}
