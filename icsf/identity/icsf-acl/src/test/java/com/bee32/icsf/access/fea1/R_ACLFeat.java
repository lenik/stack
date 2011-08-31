package com.bee32.icsf.access.fea1;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACEDao;
import com.bee32.icsf.access.alt.R_ACLDao;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;

@Scope("prototype")
@Using(IcsfIdentityUnit.class)
public class R_ACLFeat
        extends WiredDaoFeat<R_ACLFeat> {

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

    public static void main(String[] args)
            throws IOException {
        new R_ACLFeat().mainLoop(new ICoordinator<R_ACLFeat>() {
            @Override
            public void main(R_ACLFeat feat)
                    throws Exception {
                feat.listSamples();
            }
        });
    }

}
