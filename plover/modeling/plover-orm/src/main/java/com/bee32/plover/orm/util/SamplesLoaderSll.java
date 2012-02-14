package com.bee32.plover.orm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.AbstractSll;
import com.bee32.plover.site.SiteInstance;

/**
 * No-lazy: eagerly load samples.
 */
public class SamplesLoaderSll
        extends AbstractSll {

    static Logger logger = LoggerFactory.getLogger(SamplesLoaderSll.class);

    @Override
    public synchronized void startSite(SiteInstance site) {
        loadSamples();
    }

    void loadSamples() {
        ApplicationContext appctx = ThreadHttpContext.requireApplicationContext();
        SiteInstance site = appctx.getBean(SiteInstance.class);
        SamplesLoader samplesLoader = appctx.getBean(SamplesLoader.class);

        String prefix = site.getLoggingPrefix();
        logger.info(prefix + "Activate samples loader.");

        switch (site.getSamples()) {
        case WORSE:
            samplesLoader.loadSamples(DiamondPackage.WORSE);
            break;
        case NORMAL:
            samplesLoader.loadSamples(DiamondPackage.NORMAL);
            break;
        case STANDARD:
            samplesLoader.loadSamples(DiamondPackage.STANDARD);
            break;
        case NONE:
        default:
            break;
        }
    }

}
