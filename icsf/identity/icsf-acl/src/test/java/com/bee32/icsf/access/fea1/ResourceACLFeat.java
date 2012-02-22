package com.bee32.icsf.access.fea1;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.IcsfAccessUnit;
import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACEDao;
import com.bee32.icsf.access.alt.R_ACLDao;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;

@Using(IcsfAccessUnit.class)
public class ResourceACLFeat
        extends WiredDaoFeat<ResourceACLFeat> {

    @Inject
    R_ACLDao aclDao;

    @Inject
    R_ACEDao aceDao;

    @Transactional
    public void listSamples() {
        for (R_ACE ace : aceDao.list()) {
            System.out.println(ace);
        }
    }

    public static void main(String[] args)
            throws IOException {
        new ResourceACLFeat().mainLoop(new ICoordinator<ResourceACLFeat>() {
            @Override
            public void main(ResourceACLFeat feat)
                    throws Exception {
                feat.listSamples();
            }
        });
    }

}
