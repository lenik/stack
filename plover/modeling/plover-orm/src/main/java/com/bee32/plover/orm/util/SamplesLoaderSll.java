package com.bee32.plover.orm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.servlet.util.ThreadHttpContext;
import com.bee32.plover.site.AbstractSll;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.scope.PerSite;

/**
 * No-lazy: eagerly load samples.
 */
@PerSite
public class SamplesLoaderSll
        extends AbstractSll {

    static Logger logger = LoggerFactory.getLogger(SamplesLoaderSll.class);

    private static boolean loadNormalSamples = true;
    private static boolean loadWorseSamples;

    public static boolean isLoadNormalSamples() {
        return loadNormalSamples;
    }

    public static void setLoadNormalSamples(boolean loadNormalSamples) {
        SamplesLoaderSll.loadNormalSamples = loadNormalSamples;
    }

    public static boolean isLoadWorseSamples() {
        return loadWorseSamples;
    }

    public static void setLoadWorseSamples(boolean loadWorseSamples) {
        SamplesLoaderSll.loadWorseSamples = loadWorseSamples;
    }

    boolean samplesLoaded;

    @Override
    public synchronized void startSite(SiteInstance site) {
        if (!samplesLoaded) {
            loadSamples();
            samplesLoaded = true;
        }
    }

    void loadSamples() {
        ApplicationContext appctx = ThreadHttpContext.requireApplicationContext();
        SiteInstance site = appctx.getBean(SiteInstance.class);
        SamplesLoader samplesLoader = appctx.getBean(SamplesLoader.class);

        String prefix = site.getLoggingPrefix();
        logger.info(prefix + "Activate samples loader.");

        if (loadNormalSamples) {
            samplesLoader.loadSamples(DiamondPackage.NORMAL);
        }

        if (loadWorseSamples) {
            samplesLoader.loadSamples(DiamondPackage.WORSE);
        }
    }

}
