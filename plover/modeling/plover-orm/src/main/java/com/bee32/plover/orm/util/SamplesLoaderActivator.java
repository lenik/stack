package com.bee32.plover.orm.util;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.access.BootstrapException;

import com.bee32.plover.inject.InitializingService;
import com.bee32.plover.site.SiteInstance;
import com.bee32.plover.site.scope.PerSite;

/**
 * No-lazy: eagerly load samples.
 */
@PerSite
// @ScopeProxy(ScopedProxyMode.TARGET_CLASS)
public class SamplesLoaderActivator
        extends InitializingService {

    static Logger logger = LoggerFactory.getLogger(SamplesLoaderActivator.class);

    private static boolean loadNormalSamples = true;
    private static boolean loadWorseSamples;

    @Inject
    SiteInstance site;

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

    /**
     * Samples are required over all. (However, activate in CDM seems a bit too late.)
     */
    @Override
    protected void _initialize() {
        String prefix = site.getLoggingPrefix();
        logger.info(prefix + "Activate samples loader.");
        SamplesLoaderActivator samplesLoaderActivator = appctx.getBean(SamplesLoaderActivator.class);
        try {
            samplesLoaderActivator.initialize();
        } catch (Exception e) {
            throw new BootstrapException(e.getMessage(), e);
        }

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
