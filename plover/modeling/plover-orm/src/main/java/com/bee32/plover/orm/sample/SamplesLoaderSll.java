package com.bee32.plover.orm.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.orm.sample.SuperSamplePackage.Boundaries;
import com.bee32.plover.orm.sample.SuperSamplePackage.Normals;
import com.bee32.plover.orm.sample.SuperSamplePackage.Standards;
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
        SiteInstance site = ctx.bean.getBean(SiteInstance.class);
        SamplesLoader samplesLoader = ctx.bean.getBean(SamplesLoader.class);

        String prefix = site.getLoggingPrefix();
        logger.info(prefix + "Activate samples loader.");

        SamplePackageAllocation alloc = SamplePackageAllocation.BOOTSTRAP;
        SamplePackage max;
        switch (site.getSamples()) {
        case STANDARD:
            max = alloc.getObject(Standards.class);
            break;
        case NORMAL:
            max = alloc.getObject(Normals.class);
            break;
        case BOUNDARIES:
            max = alloc.getObject(Boundaries.class);
            break;
        case NONE:
        default:
            max = null;
        }

        if (max != null)
            samplesLoader.loadSamples(max);
    }

}
