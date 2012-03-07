package com.bee32.sem.uber;

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.principal.IcsfPrincipalSamples;
import com.bee32.icsf.principal.Users;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoFeat;
import com.bee32.plover.test.ICoordinator;

/**
 * @see IcsfPrincipalSamples
 */
@Using(SEMUberUnit.class)
public class SEMUberSamplesLoaderFeat
        extends WiredDaoFeat<SEMUberSamplesLoaderFeat> {

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
        return "zjhf.lo";
    }

    public static void main(String[] args)
            throws IOException {
        new SEMUberSamplesLoaderFeat().mainLoop(new ICoordinator<SEMUberSamplesLoaderFeat>() {
            @Override
            public void main(SEMUberSamplesLoaderFeat feat)
                    throws Exception {
                feat.listSystemUsers();
            }
        });
    }

}
