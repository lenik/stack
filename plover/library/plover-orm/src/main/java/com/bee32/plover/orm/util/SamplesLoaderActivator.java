package com.bee32.plover.orm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SamplesLoaderActivator
        implements ApplicationContextAware {

    private static boolean loadNormalSamples;
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
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {

        if (loadNormalSamples) {
            SamplesLoader samplesLoader = applicationContext.getBean(SamplesLoader.class);
            samplesLoader.loadNormalSamples();
        }

        if (loadWorseSamples) {
            SamplesLoader samplesLoader = applicationContext.getBean(SamplesLoader.class);
            samplesLoader.loadWorseSamples();
        }

    }

}
