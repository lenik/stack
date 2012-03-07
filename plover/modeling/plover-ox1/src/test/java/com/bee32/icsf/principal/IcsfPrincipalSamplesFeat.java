package com.bee32.icsf.principal;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;

/**
 * @see IcsfPrincipalSamples
 */
@Using(IcsfPrincipalUnit.class)
public class IcsfPrincipalSamplesFeat
        extends WiredDaoFeat<IcsfPrincipalSamplesFeat> {

    @Inject
    Users users;
    @Inject
    IcsfPrincipalSamples principals;

    @Transactional(readOnly = true)
    public void listSystemUsers() {
        for (Entity<?> sample : users.getSamples(false)) {
            System.out.println(sample);
        }
    }

    @Override
    protected String getFeatureSite() {
        return "play.lo";
    }

    public static void main(String[] args)
            throws IOException {
        new IcsfPrincipalSamplesFeat().mainLoop(new ICoordinator<IcsfPrincipalSamplesFeat>() {
            @Override
            public void main(IcsfPrincipalSamplesFeat feat)
                    throws Exception {
                feat.listSystemUsers();
            }
        });
    }
}
