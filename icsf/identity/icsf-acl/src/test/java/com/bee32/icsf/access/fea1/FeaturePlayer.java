package com.bee32.icsf.access.fea1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bee32.icsf.access.alt.R_ACE;
import com.bee32.icsf.access.alt.R_ACEDao;
import com.bee32.icsf.access.alt.R_ACLDao;
import com.bee32.plover.inject.cref.Import;
import com.bee32.plover.inject.cref.ScanTestxContext;
import com.bee32.plover.inject.spring.ApplicationContextBuilder;
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
            throws Exception {

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

            try {
                FeaturePlayer player = ApplicationContextBuilder.create(FeaturePlayer.class);

                player.listSamples();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Press enter to try again");
            stdin.readLine();
        }
    }

}
