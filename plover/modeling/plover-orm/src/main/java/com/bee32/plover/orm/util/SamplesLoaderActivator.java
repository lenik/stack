package com.bee32.plover.orm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.inject.ActivatorBean;
import com.bee32.plover.site.scope.PerSite;

/**
 * No-lazy: eagerly load samples.
 */
@PerSite
public class SamplesLoaderActivator
        extends ActivatorBean {

    static Logger logger = LoggerFactory.getLogger(SamplesLoaderActivator.class);

    private static boolean loadNormalSamples = true;
    private static boolean loadWorseSamples;

    public static boolean isLoadNormalSamples() {
        return loadNormalSamples;
    }

    public static void setLoadNormalSamples(boolean loadNormalSamples) {
        SamplesLoaderActivator.loadNormalSamples = loadNormalSamples;
    }

    public static boolean isLoadWorseSamples() {
        return loadWorseSamples;
    }

    public static void setLoadWorseSamples(boolean loadWorseSamples) {
        SamplesLoaderActivator.loadWorseSamples = loadWorseSamples;
    }

    @Override
    public void activate() {
        logger.info("Activate samples loader");

        if (loadNormalSamples) {
            SamplesLoader samplesLoader = appctx.getBean(SamplesLoader.class);
            samplesLoader.loadNormalSamples();
        }

        if (loadWorseSamples) {
            SamplesLoader samplesLoader = appctx.getBean(SamplesLoader.class);
            samplesLoader.loadWorseSamples();
        }
    }

}
